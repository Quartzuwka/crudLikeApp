package com.example.fortests.second

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun SecondScreen(
    viewModel: SecondViewModel = hiltViewModel(),
    onDuckClicked: (str: String) -> Unit
) {
    LaunchedEffect(key1 = viewModel, block = { viewModel.fetchInitialImages() })
    val viewState by viewModel.viewState.collectAsState()

    val scrollState = viewModel.scrollState.value


//    val fetchNextPage: Boolean by remember {
//        derivedStateOf {
//            val currentCharacterCount =
//                (viewState as? DuckViewState.GridDisplay)?.ducks?.size
//                    ?: return@derivedStateOf false
//            val lastDisplayedIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
//                ?: return@derivedStateOf false
//            return@derivedStateOf lastDisplayedIndex >= currentCharacterCount - 20
//        }
//    }
//
//    LaunchedEffect(key1 = fetchNextPage, block = {
//        if (fetchNextPage) viewModel.fetchNextImages()
//    })


    when (val state = viewState) {
        DuckViewState.Loading -> LoadingState()
        is DuckViewState.GridDisplay -> {
            Column {
                LazyVerticalGrid(
                    state = scrollState,
                    modifier = Modifier.padding(bottom = 90.dp),
                    contentPadding = PaddingValues(all = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    columns = GridCells.Fixed(2),
                    content = {
                        items(
                            items = state.ducks
                        ) { duck ->
                            DuckImage(
                                onClick = { onDuckClicked(duck) },
                                imageUrl = "https://random-d.uk/api/${duck}.jpg"
                            )
                        }
                    })
            }
        }
    }
}