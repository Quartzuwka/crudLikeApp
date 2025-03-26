package com.example.fortests.second

import android.app.Application
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fortests.duckrepo.DuckImagesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(
    application: Application, private val duckImagesRepo: DuckImagesRepo
) : ViewModel() {

    private val _viewState = MutableStateFlow<DuckViewState>(DuckViewState.Loading)
    val viewState: StateFlow<DuckViewState> = _viewState.asStateFlow()

    val scrollState = mutableStateOf(LazyGridState())

    private val fetchedDucks = mutableListOf<String>()

    fun fetchInitialImages() = viewModelScope.launch {
        if (fetchedDucks.isNotEmpty()) return@launch
        val initialData = duckImagesRepo.fetchImages()
        _viewState.update {
            return@update DuckViewState.GridDisplay(ducks = initialData)
        }
    }

//    fun fetchNextImages() = viewModelScope.launch {
//        val data = duckImagesRepo.fetchNextData()
//        _viewState.update { currentState ->
//            when (currentState) {
//                is DuckViewState.GridDisplay -> {
//                    val updatedDucks = currentState.ducks + data // Добавляем новые данные
//                    DuckViewState.GridDisplay(ducks = updatedDucks)
//                }
//
//                else -> currentState // Возвращаем исходное состояние, если оно не GridDisplay
//            }
//        }
//
//    }


}


//
//}

