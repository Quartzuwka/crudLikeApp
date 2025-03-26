package com.example.fortests.duckrepo


import com.example.fortests.network.KtorClient
import javax.inject.Inject

class DuckImagesRepo @Inject constructor(private val ktorClient: KtorClient) {

    var list: MutableList<String> = mutableListOf()

//    suspend fun fetchNextData(): List<String> {
//        for (i in 1..20) list.removeAt(0)
//        return list.take(20)
//    }

    suspend fun fetchAmountOfImages(): Int {
        return ktorClient.getAmountOfImages().image_count
    }

    suspend fun fetchImages(): List<String> {
        list.addAll(ktorClient.getAmountOfImages().images)
        return list
    }

}

