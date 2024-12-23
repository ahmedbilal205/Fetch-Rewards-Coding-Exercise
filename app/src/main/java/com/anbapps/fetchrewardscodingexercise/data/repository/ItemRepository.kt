package com.anbapps.fetchrewardscodingexercise.data.repository

import com.anbapps.fetchrewardscodingexercise.data.model.Item
import com.anbapps.fetchrewardscodingexercise.data.remote.ApiService

class ItemRepository(private val apiService: ApiService) {
    // Fetches and filters items from the API
    suspend fun getFilteredItems(): List<Item> {
        return apiService.getItems()
            .filter { it.name?.isNotBlank() == true }  // Exclude items with null or blank names
            .sortedWith(compareBy({ it.listId }, { it.name })) // Sort by listId and then by name
    }
}
