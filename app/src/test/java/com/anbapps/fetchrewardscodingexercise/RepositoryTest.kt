package com.anbapps.fetchrewardscodingexercise

import com.anbapps.fetchrewardscodingexercise.data.model.Item
import com.anbapps.fetchrewardscodingexercise.data.remote.ApiService
import com.anbapps.fetchrewardscodingexercise.data.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class RepositoryTest {
    //What am I Testing?
    //Filtering Logic: Ensuring that items with name that are blank or null are excluded from the result.
    //Sorting Logic: Verifying that the remaining items are sorted:
    //First by listId (ascending).
    //Then by name (alphabetically within the same listId).
    //Integration with Mocked API: Ensuring the repository processes the data provided by the mocked ApiService.

    @Test
    fun `repository filters and sorts items correctly`(): Unit = runTest {
        val mockApiService = mockk<ApiService> {
            // Mocking the API response to return a list of items
            coEvery { getItems() } returns listOf(
                Item(1, 2, "Item B"),
                Item(2, 2, ""),
                Item(3, 1, "Item A"),
                Item(4, 1, null)
            )
        }

        //Instantiate the repository with the mocked ApiService
        val repository = ItemRepository(mockApiService)

        //Call the repository method that filters and sorts items
        val result = repository.getFilteredItems()

        //Assert the filtered list has the correct number of items
        assertEquals(2, result.size)

        //Assert the items are sorted by `listId` and then by `name`
        assertEquals(listOf(
            Item(3, 1, "Item A"),
            Item(1, 2, "Item B")
        ), result)
    }
}