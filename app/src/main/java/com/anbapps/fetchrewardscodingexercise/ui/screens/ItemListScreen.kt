package com.anbapps.fetchrewardscodingexercise.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.anbapps.fetchrewardscodingexercise.ui.components.ItemCard

@Composable
fun ItemListScreen(viewModel: ItemListViewModel = hiltViewModel()) {
    val items by viewModel.items.collectAsState()

    LazyColumn {
        items(items) { item ->
            ItemCard(item = item)
        }
    }
}
