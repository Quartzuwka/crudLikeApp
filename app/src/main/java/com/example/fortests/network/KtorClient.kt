package com.example.fortests.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

import io.ktor.client.plugins.logging.Logger

import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

class KtorClient {

    @Serializable
    data class ApiResponse(
        val http: List<String>? = null,
        val image_count: Int = 0,
        val images: List<String>
    )

    fun ApiResponse.toApiResponse(): ApiResponse{
        return ApiResponse(
            http = http,
            image_count = image_count,
            images = images
        )
    }

    private val client = HttpClient(OkHttp) {
        defaultRequest { url("https://random-d.uk/api/v2/") }

        install(Logging) {
            logger = Logger.SIMPLE
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        expectSuccess = true
    }

    suspend fun getAmountOfImages(): ApiResponse {
            return client.get("list")
                .body<ApiResponse>()
                .toApiResponse()
    }
}

