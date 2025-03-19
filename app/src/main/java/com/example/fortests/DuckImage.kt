package com.example.fortests

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage

@Composable
fun RandomDuckImage(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Character image",

    )
}

@Preview
@Composable
fun Ay(modifier: Modifier = Modifier) {
    RandomDuckImage("https://random-d.uk/api/186.jpg")
}