package com.example.fortests.duckrepo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.fortests.network.KtorClient
import javax.inject.Inject
import kotlin.math.min // Import min function

class DuckImagePagingSource @Inject constructor(private val ktorClient: KtorClient) : PagingSource<Int, String>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        // Use the page number requested by Paging library, default to 1 if null
        val currentPage = params.key ?: 1
        // Use the load size requested by Paging library (from PagingConfig)
        val loadSize = params.loadSize

        return try {
            // --- Inefficient part: Fetching all images every time ---
            // Ideally, modify KtorClient to fetch only the needed page.
            // Example: val pageData = ktorClient.getImagesPage(page = currentPage, size = loadSize)
            val allImages = ktorClient.getAmountOfImages().images
            // --- End of inefficient part ---

            // Calculate start and end index for the current page based on *all* images
            val startIndex = (currentPage - 1) * loadSize
            // Ensure endIndex doesn't go beyond the list size
            val endIndex = min(startIndex + loadSize, allImages.size)

            // Get the sublist for the current page
            // Handle cases where startIndex might be out of bounds (already loaded all pages)
            val pageData = if (startIndex < allImages.size) {
                allImages.subList(startIndex, endIndex)
            } else {
                emptyList() // Return empty list if requested page is beyond available data
            }

            // Determine previous page key
            val prevKey = if (currentPage == 1) null else currentPage - 1

            // Determine next page key: null if this page is the last one
            val nextKey = if (pageData.isEmpty() || endIndex >= allImages.size) {
                null // No more pages to load
            } else {
                currentPage + 1
            }

            // --- CORRECT: Return LoadResult.Page ---
            LoadResult.Page(
                data = pageData,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            // --- CORRECT: Return LoadResult.Error on failure ---
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        // Try to find the page key closest to the last accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}