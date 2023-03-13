package com.example.android_bankuish_challenge.data.model

import com.squareup.moshi.Json

/**
 * @author Estefania Figueroa Buitrago
 * estefaniafigbui@gmail.com
 *
 * This data class defines an Api Response which includes the server original response.
 * We will need the [ArrayList] of [GithubRepository] objects for the [RecyclerView].
 */
data class ApiResponse (
    @Json(name = "total_count") val totalCount: String,
    @Json(name = "incomplete_results") val incompleteResults: String,
    @Json(name = "items") val items: ArrayList<GithubRepository>
)