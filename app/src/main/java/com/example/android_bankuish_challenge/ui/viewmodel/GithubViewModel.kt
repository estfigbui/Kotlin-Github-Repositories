package com.example.android_bankuish_challenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.android_bankuish_challenge.data.model.ApiResponse
import com.example.android_bankuish_challenge.data.model.GithubRepository
import com.example.android_bankuish_challenge.data.network.GithubApiService
import com.example.android_bankuish_challenge.paging.GithubPagingSource
import com.example.android_bankuish_challenge.utils.LANGUAGE
import com.example.android_bankuish_challenge.utils.NETWORK_PAGE_SIZE
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Estefania Figueroa Buitrago
 * estefaniafigbui@gmail.com
 *
 * Shared [ViewModel]
 */

// Enum Class to provide the user with network feedback in a visual manner
enum class GithubApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val api: GithubApiService
) : ViewModel() {

    companion object {
        const val TAG = "GithubViewModel"
    }

    // Internal and external variables for the current selected repository
    private var _currentRepository: MutableLiveData<GithubRepository> =
        MutableLiveData<GithubRepository>()
    val currentRepository: LiveData<GithubRepository>
        get() = _currentRepository

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<GithubApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<GithubApiStatus>
        get() = _status

    // Internal and external variables for the repositories list
    private var _repositoriesData = MutableLiveData<List<GithubRepository>>()
    val repositoriesData: LiveData<List<GithubRepository>>
        get() = _repositoriesData

    // Stream of paged data from the PagingSource implementation.
    val repositoriesList = Pager(PagingConfig(1)) {
        GithubPagingSource(api)
    }.flow.cachedIn(viewModelScope)

    // Call getGithubRepositories() on init so we can display status immediately.
    init {
        getGithubRepositories()
    }

    // Gets Github repositories information from the Github API Retrofit service and updates the
    // [GithubRepository] [List] [LiveData].
    private fun getGithubRepositories() {
        // We use Gson since the data we need comes nested inside the response
        val gson = Gson()

        viewModelScope.launch {
            _status.value = GithubApiStatus.LOADING
            try {
                val response = api.getRepositories(LANGUAGE, NETWORK_PAGE_SIZE, "1")
                val apiResponse = gson.fromJson(response, ApiResponse::class.java)
                _repositoriesData.value = apiResponse.items
                _status.value = GithubApiStatus.DONE
            } catch (e: Exception) {
                _status.value = GithubApiStatus.ERROR
                _repositoriesData.value = listOf()
            }
        }
    }

    // Updates the value of the current repository selected by the user, so the correct details are shown in the FragmentRepositoryDetails
    fun updateCurrentRepository(repository: GithubRepository) {
        _currentRepository.value = repository
    }

    fun updateRepositories() {
        getGithubRepositories()
    }
}