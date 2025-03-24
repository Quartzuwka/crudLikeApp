package com.example.fortests.second

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fortests.components.DuckImage
import com.example.fortests.components.LoadingState

sealed interface DuckViewState {
    object Loading : DuckViewState
    data class GridDisplay(
        val ducks: List<String> = emptyList()
    ) : DuckViewState
}

@Composable
fun SecondScreen(viewModel: SecondViewModel = hiltViewModel()) {
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = viewModel, block = { viewModel.fetchInitialImages() })

    val scrollState = rememberLazyGridState()
//    val fetchNextPage: Boolean by remember {
//        derivedStateOf {
//            val currentCharacterCount =
//                (viewState as? UserViewState.GridDisplay)?.characters?.size
//                    ?: return@derivedStateOf false
//            val lastDisplayedIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
//                ?: return@derivedStateOf false
//            return@derivedStateOf lastDisplayedIndex >= currentCharacterCount - 10
//        }
//    }

//    LaunchedEffect(key1 = fetchNextPage, block = {
//        if (fetchNextPage) viewModel.fetchNextData()
//    })

    when (val state = viewState) {
        DuckViewState.Loading -> LoadingState()
        is DuckViewState.GridDisplay -> {
            Column {
                LazyVerticalGrid(
                    state = scrollState,
                    contentPadding = PaddingValues(all = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    columns = GridCells.Fixed(2),
                    content = {
                        items(
                            items = state.ducks
                        ) { duck ->
                            DuckImage(modifier = Modifier, imageUrl = "https://random-d.uk/api/${duck}.jpg")
                        }
                    })
            }
        }
    }
}