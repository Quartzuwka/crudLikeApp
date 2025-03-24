package com.example.fortests.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun RandomDuckImage(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Duck image",
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)

    )
}

@Composable
fun DuckImage(imageUrl: String, onClick: () -> Unit) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Duck image",
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .clickable(onClick = onClick)
    )
}