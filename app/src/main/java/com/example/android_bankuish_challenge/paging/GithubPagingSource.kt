package com.example.android_bankuish_challenge.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android_bankuish_challenge.data.model.ApiResponse
import com.example.android_bankuish_challenge.data.model.GithubRepository
import com.example.android_bankuish_challenge.data.network.GithubApiService
import com.example.android_bankuish_challenge.utils.LANGUAGE
import com.example.android_bankuish_challenge.utils.NETWORK_PAGE_SIZE
import com.google.gson.Gson
import retrofit2.HttpException

/**
 * @author Estefania Figueroa Buitrago
 * estefaniafigbui@gmail.com
 *
 * [PagingSource] implementation for pageable data loading consuming network service.
 */

class GithubPagingSource(private val api: GithubApiService) :
    PagingSource<Int, GithubRepository>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepository> {
        val gson = Gson()
        return try {
            val currentPage = params.key ?: 1
            val response = api.getRepositories(LANGUAGE, NETWORK_PAGE_SIZE, currentPage.toString())
            val apiResponse = gson.fromJson(response, ApiResponse::class.java)
            val data = apiResponse.items
            val responseData = mutableListOf<GithubRepository>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GithubRepository>): Int? {
        return null
    }
}