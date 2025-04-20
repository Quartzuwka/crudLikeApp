package com.example.fortests.duckrepo


import com.example.fortests.network.KtorClient
import javax.inject.Inject

class DuckImagesRepo @Inject constructor(private val ktorClient: KtorClient) {

    val pagingSource: DuckImagePagingSource by lazy { DuckImagePagingSource(ktorClient) }

}

