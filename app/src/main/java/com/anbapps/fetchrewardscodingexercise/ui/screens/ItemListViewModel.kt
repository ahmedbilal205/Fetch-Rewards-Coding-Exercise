package com.anbapps.fetchrewardscodingexercise.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anbapps.fetchrewardscodingexercise.data.model.Item
import com.anbapps.fetchrewardscodingexercise.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _selectedListIds = MutableStateFlow<Set<Int>>(emptySet())
    val selectedListIds: StateFlow<Set<Int>> = _selectedListIds

    init {
        fetchItems()
    }

    fun fetchItems() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repository.getGroupedItems()
            _uiState.value = result.fold(
                onSuccess = { items ->
                    // Initialize selectedListIds to include all `listId`s
                    _selectedListIds.value = items.keys
                    UiState.Success(items)
                },
                onFailure = { UiState.Error(it.localizedMessage ?: "Unknown Error") }
            )
        }
    }

    fun toggleListId(listId: Int) {
        // Toggle the selection of a listId
        _selectedListIds.value = if (_selectedListIds.value.contains(listId)) {
            _selectedListIds.value - listId
        } else {
            _selectedListIds.value + listId
        }
    }

    fun getFilteredGroupedItems(): Flow<Map<Int, List<Item>>> {
        return combine(
            uiState.filterIsInstance<UiState.Success>(),
            selectedListIds
        ) { state, selectedIds ->
            state.data.filterKeys { it in selectedIds }
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: Map<Int, List<Item>>) : UiState()
    data class Error(val message: String) : UiState()
}

