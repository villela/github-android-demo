package com.matheusvillela.githubdemoapp.data

import com.matheusvillela.githubdemoapp.data.dto.GitHubRepoDto
import com.matheusvillela.githubdemoapp.data.dto.GitHubRepoSearchContainerDto
import com.matheusvillela.githubdemoapp.data.dto.GitHubUserDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface Api {
    @GET("repositories")
    fun getRepositories(
        @Query("since") since: Int? = null
    ): Single<List<GitHubRepoDto>>

    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("since") since: Int? = null
    ): Single<GitHubRepoSearchContainerDto>

    @GET("users/{user}")
    fun getUser(@Path("user") userLogin: String): Single<GitHubUserDto>
}
