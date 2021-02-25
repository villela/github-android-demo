package com.matheusvillela.githubdemoapp.data

import com.matheusvillela.githubdemoapp.data.dto.GitHubRepoDto
import com.matheusvillela.githubdemoapp.data.dto.UserDto
import io.reactivex.Single

interface GitHubRepository {
    fun getGitHubRepos(page: Int, perPage: Int, query: String? = null): Single<List<GitHubRepoDto>>
    fun getUser(userLogin: String): Single<UserDto>
}