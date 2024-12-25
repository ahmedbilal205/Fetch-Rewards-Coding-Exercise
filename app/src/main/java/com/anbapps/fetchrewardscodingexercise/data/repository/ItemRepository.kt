package com.anbapps.fetchrewardscodingexercise.data.repository

import com.anbapps.fetchrewardscodingexercise.data.model.Item
import com.anbapps.fetchrewardscodingexercise.data.remote.ApiService

class ItemRepository(private val apiService: ApiService) {
    suspend fun getGroupedItems(): Result<Map<Int, List<Item>>> {
        return try {
            val items = apiService.getItems()
                .filter { !it.name.isNullOrBlank() }
                .sortedWith(compareBy({ it.listId }, { it.name }))
                .groupBy { it.listId }
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}