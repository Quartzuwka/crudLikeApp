package com.example.fortests.second

import android.app.Application
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.fortests.duckrepo.DuckImagesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(
    application: Application,
    private val duckImagesRepo: DuckImagesRepo,
) : ViewModel() {
    val duckPager: Flow<PagingData<String>> = Pager(
        PagingConfig(
            pageSize = 20,
        )
    ) { duckImagesRepo.pagingSource }
        .flow
        .cachedIn(viewModelScope)

    val scrollState = mutableStateOf(LazyGridState())
}

