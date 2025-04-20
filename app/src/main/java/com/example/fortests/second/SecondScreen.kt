package com.example.fortests.second

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.fortests.components.DuckImage
import com.example.fortests.components.LoadingState

sealed interface DuckViewState {
    object Loading : DuckViewState
    data class GridDisplay(
        val ducks: List<String> = emptyList()
    ) : DuckViewState
}

@Composable
fun SecondScreen(
    viewModel: SecondViewModel = hiltViewModel(),
    onDuckClicked: (str: String) -> Unit,
) {
    val ducks = viewModel.duckPager.collectAsLazyPagingItems()

    val scrollState by viewModel.scrollState

    if (ducks.loadState.refresh is LoadState.Loading)
        LoadingState()
    else
        Column {
            LazyVerticalGrid(
                state = scrollState,
                modifier = Modifier.padding(bottom = 90.dp),
                contentPadding = PaddingValues(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                columns = GridCells.Fixed(2),
            ) {
                items(ducks.itemCount) { index ->
                    val duck = ducks[index]
                    if (duck == null)
                    // Use any placeholder you want for data that is already requested, but not yet loaded.
                    // Also see PagingConfig.enablePlaceholders
                        CircularProgressIndicator()
                    else
                        DuckImage(
                            onClick = { onDuckClicked(duck) },
                            imageUrl = "https://random-d.uk/api/${duck}.jpg",
                        )
                }
            }
        }
}