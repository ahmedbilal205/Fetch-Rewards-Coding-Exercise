package com.anbapps.fetchrewardscodingexercise.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anbapps.fetchrewardscodingexercise.data.model.Item
import com.anbapps.fetchrewardscodingexercise.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {
    // Holds the state of the items to be displayed
    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    init {
        fetchItems() // Fetch items when ViewModel is initialized
    }

    // Fetches items from the repository and updates the state
    private fun fetchItems() {
        viewModelScope.launch {
            _items.value = repository.getFilteredItems()
        }
    }
}
