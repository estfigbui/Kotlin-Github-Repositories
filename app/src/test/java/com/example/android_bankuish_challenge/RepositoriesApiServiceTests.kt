package com.example.android_bankuish_challenge

import com.example.android_bankuish_challenge.data.model.ApiResponse
import com.example.android_bankuish_challenge.data.model.network.GithubApiService
import com.google.gson.Gson

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class RepositoriesApiServiceTests : BaseTest() {
    private lateinit var service: GithubApiService

    // we use a false URL to build our service
    @Before
    fun setup() {
        val url = mockWebServer.url("/")

        service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(GithubApiService::class.java)
    }

    // API response validation
    @Test
    fun api_service() {
        val gson = Gson()
        enqueue("github_repositories.json")

        runBlocking {
            val response = service.getRepositories("kotlin", "2", "1")
            val gsonResponse = gson.fromJson(response, ApiResponse::class.java)
            val apiResponse = gsonResponse.items
            assertNotNull(apiResponse)
            assertTrue("The list was empty", apiResponse.isNotEmpty())
            assertEquals("The IDs did not match", "3432266", apiResponse[0].id)
        }
    }
}