package com.example.fortests.fourth

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.fortests.duckrepo.DuckImagesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    application: Application, private val duckImagesRepo: DuckImagesRepo
): ViewModel() {
    private val _favoriteDucks = MutableStateFlow<List<String>>(emptyList())
    val favoriteDucks: StateFlow<List<String>> = _favoriteDucks.asStateFlow()

    fun toggleFavorite(duckId: String) {
        _favoriteDucks.update { current ->
            if (duckId in current) current - duckId else current + duckId
        }
    }
}