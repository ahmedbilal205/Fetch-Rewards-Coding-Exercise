package com.anbapps.fetchrewardscodingexercise

import com.anbapps.fetchrewardscodingexercise.data.model.Item
import com.anbapps.fetchrewardscodingexercise.data.remote.ApiService
import com.anbapps.fetchrewardscodingexercise.data.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.fail
import org.junit.Test

class RepositoryTest {

    //What am I Testing?
    //Filtering Logic: Ensuring that items with name that are blank or null are excluded from the result.
    //Sorting Logic: Verifying that the remaining items are sorted : by listId and name (implicitly via the grouping logic).
    //Integration with Mocked API: Ensuring the repository processes the data provided by the mocked ApiService.

    @Test
    fun `repository groups items by listId correctly`() = runTest {
        // Mock API response
        val mockItems = listOf(
            Item(id = 1, listId = 1, name = "Item A"),
            Item(id = 2, listId = 1, name = "Item B"),
            Item(id = 3, listId = 2, name = "Item C"),
            Item(id = 4, listId = 3, name = null),  // Should be filtered out
            Item(id = 5, listId = 2, name = "Item D"),
            Item(id = 6, listId = 3, name = "")    // Should be filtered out
        )

        val mockApiService = mockk<ApiService> {
            coEvery { getItems() } returns mockItems
        }

        val repository = ItemRepository(mockApiService)

        // Perform the repository logic
        val groupedItemsResult = repository.getGroupedItems()

        // Assertions
        groupedItemsResult.fold(
            onSuccess = { groupedItems ->
                // Expected grouped result
                val expectedGroupedItems = mapOf(
                    1 to listOf(
                        Item(id = 1, listId = 1, name = "Item A"),
                        Item(id = 2, listId = 1, name = "Item B")
                    ),
                    2 to listOf(
                        Item(id = 3, listId = 2, name = "Item C"),
                        Item(id = 5, listId = 2, name = "Item D")
                    )
                )

                // Verify size
                assertEquals(2, groupedItems.size)

                // Verify group by listId
                assertEquals(expectedGroupedItems[1], groupedItems[1])
                assertEquals(expectedGroupedItems[2], groupedItems[2])

                // Verify filtering (No null or blank names)
                assertFalse(groupedItems.containsKey(3)) // `listId = 3` should be excluded
            },
            onFailure = { error ->
                fail("Expected success but got failure: ${error.localizedMessage}")
            }
        )
    }
}