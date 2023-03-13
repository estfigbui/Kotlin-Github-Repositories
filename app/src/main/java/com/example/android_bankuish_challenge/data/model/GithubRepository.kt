package com.example.android_bankuish_challenge.data.model

import com.squareup.moshi.Json
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Estefania Figueroa Buitrago
 * estefaniafigbui@gmail.com
 *
 * This data class defines the [GithubRepository] object.
 * Since [Owner] has multiple fields, we define it as well as a data class.
 */
data class GithubRepository (
    val id: String,
    val name: String,
    @Json(name = "full_name") val fullName: String,
    val owner: Owner,
    val description: String,
    val stargazers_count: String,
    val forks: String
)