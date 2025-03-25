package com.example.fortests.fourth

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FavoritesViewModel: ViewModel() {
    private val _favoriteDucks = MutableStateFlow<List<String>>(emptyList())
    val favoriteDucks: StateFlow<List<String>> = _favoriteDucks.asStateFlow()

    fun toggleFavorite(duckId: String) {
        _favoriteDucks.update { current ->
            if (duckId in current) current - duckId else current + duckId
        }
    }
}