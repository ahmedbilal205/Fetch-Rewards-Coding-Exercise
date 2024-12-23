package com.anbapps.fetchrewardscodingexercise.data.remote

import com.anbapps.fetchrewardscodingexercise.data.model.Item
import retrofit2.http.GET

interface ApiService {
    // Endpoint to fetch a list of items from the API
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}
