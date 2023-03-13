package com.example.android_bankuish_challenge.data.network

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Estefania Figueroa Buitrago
 * estefaniafigbui@gmail.com
 */

// A public interface that exposes the [getRepositories] method
interface GithubApiService {
    /**
     * Returns a [JSON] string according to the parameters passed with the @Query annotation.
     * This method can be called from a Coroutine.
     * The @GET annotation indicates that the "repositories" endpoint will be requested with the GET HTTP method.
     */
    @GET("repositories")
    suspend fun getRepositories(
        @Query("q") param_q: String?,
        @Query("per_page") param_perpage: String?,
        @Query("page") param_page: String?
    ): String
}