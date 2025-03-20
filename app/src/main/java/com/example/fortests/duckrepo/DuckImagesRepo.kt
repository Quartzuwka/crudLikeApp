package com.example.fortests.duckrepo


import com.example.fortests.network.KtorClient
import javax.inject.Inject

class DuckImagesRepo @Inject constructor(private val ktorClient: KtorClient) {


    suspend fun fetchAmountOfImages(): Int {
        return ktorClient.getAmountOfImages().image_count
    }

    suspend fun fetchImages(): List<String>? {
        return ktorClient.getAmountOfImages().images
    }



    companion object
}

