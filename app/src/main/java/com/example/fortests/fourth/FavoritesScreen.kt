package com.example.fortests.fourth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage

@Composable
fun FavoritesScreen(onClick: (img: String) -> Unit, vm: FavoritesViewModel) {
    val favoriteDucks by vm.favoriteDucks.collectAsStateWithLifecycle(emptyList())

    val scrollState = rememberLazyGridState()
    val columns = if (favoriteDucks.size == 1) 1 else 2

    LazyVerticalGrid(
        state = scrollState,
        columns = GridCells.Fixed(columns),
        modifier = Modifier.fillMaxSize().padding(bottom = 90.dp),
        horizontalArrangement = Arrangement.Center,
        content = {
                items(items = favoriteDucks) { duck ->
                    AsyncImage(
                        model = "https://random-d.uk/api/${duck}.jpg",
                        contentDescription = "Duck image",
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                    )
                }
        }
    )

}




