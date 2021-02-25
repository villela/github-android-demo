package com.matheusvillela.githubdemoapp.data

import com.matheusvillela.githubdemoapp.data.dto.GitHubRepoDto
import com.matheusvillela.githubdemoapp.data.dto.UserDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface Api {

    @GET("repositories")
    fun getRepositories(
        @Query("page") currentPage: Int? = null,
        @Query("per_page") query: Int? = null,
        @Query("query") perPage: String? = null
    ): Single<List<GitHubRepoDto>>

    @GET("users/{user}")
    fun getUser(@Path("user") userLogin: String): Single<UserDto>
}
