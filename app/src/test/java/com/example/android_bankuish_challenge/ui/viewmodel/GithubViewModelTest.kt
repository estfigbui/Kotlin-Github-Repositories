package com.example.android_bankuish_challenge.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android_bankuish_challenge.data.model.ApiResponse
import com.example.android_bankuish_challenge.data.model.GithubRepository
import com.example.android_bankuish_challenge.data.model.Owner
import com.example.android_bankuish_challenge.data.network.GithubApiService
import com.example.android_bankuish_challenge.viewmodel.GithubViewModel
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

@ExperimentalCoroutinesApi
internal class GithubViewModelTest : BaseTest() {

    private lateinit var service: GithubApiService
    private lateinit var githubViewModel: GithubViewModel

    // We can also carry out tests with the Mockk library
    @RelaxedMockK
    private lateinit var githubApiService: GithubApiService

    // For LiveData
    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        // we use a false URL to build our service
        val url = mockWebServer.url("/")

        service = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(GithubApiService::class.java)

        // Coroutines
        Dispatchers.setMain(Dispatchers.Unconfined)

        // Initialize ViewModel with the variable that we labeled with RelaxedMockk (it could have been 'service',
        // but I want to show the procedure with Mockk).
        githubViewModel = GithubViewModel(githubApiService)
    }

    // API response validation
    @Test
    fun `check that we get a valid response from API service`() {
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

    @Test
    fun `update Live Data value with current repository information`() = runTest {
        // Given
        val currentRepository = GithubRepository("3432266",
            "kotlin",
            "JetBrains/kotlin",
            Owner("878437",
                "JetBrains",
                "https://avatars.githubusercontent.com/u/878437?v=4",
                "https://github.com/JetBrains"),
            "The Kotlin Programming Language.",
            "44136",
            "5460")

        // When
        githubViewModel.updateCurrentRepository(currentRepository)

        // Then
        assert(githubViewModel.currentRepository.value == currentRepository)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }
}