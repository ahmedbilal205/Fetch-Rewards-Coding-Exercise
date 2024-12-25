package com.anbapps.fetchrewardscodingexercise.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anbapps.fetchrewardscodingexercise.ui.components.ItemCard

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ItemListScreen(viewModel: ItemListViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val selectedListIds by viewModel.selectedListIds.collectAsState()

    when (uiState) {
        is UiState.Loading -> {
            // Show loading indicator
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            val groupedItems = (uiState as UiState.Success).data
            val filteredGroupedItems by viewModel.getFilteredGroupedItems().collectAsState(
                initial = emptyMap()
            )

            Column {
                // Checkboxes for filtering
                Text(
                    text = "Select Groups",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                FlowRow(
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    groupedItems.keys.forEach { listId ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = selectedListIds.contains(listId),
                                onCheckedChange = { viewModel.toggleListId(listId) }
                            )
                            Text(
                                text = "List ID: $listId",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                // Display filtered items
                LazyColumn {
                    filteredGroupedItems.forEach { (listId, items) ->
                        item {
                            Text(
                                text = "List ID: $listId",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        items(items) { item ->
                            ItemCard(item = item) {}
                        }
                    }
                }
            }
        }

        is UiState.Error -> {
            // Show error message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = (uiState as UiState.Error).message,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.fetchItems() }) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}