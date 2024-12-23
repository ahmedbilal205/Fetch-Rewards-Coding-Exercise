package com.anbapps.fetchrewardscodingexercise.di

import com.anbapps.fetchrewardscodingexercise.data.remote.ApiClient
import com.anbapps.fetchrewardscodingexercise.data.remote.ApiService
import com.anbapps.fetchrewardscodingexercise.data.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApiService(): ApiService = ApiClient.apiService

    @Provides
    fun provideItemRepository(apiService: ApiService): ItemRepository =
        ItemRepository(apiService)
}
