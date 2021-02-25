package com.matheusvillela.githubdemoapp.data

import com.matheusvillela.githubdemoapp.data.dto.GitHubRepoDto
import com.matheusvillela.githubdemoapp.data.dto.UserDto
import io.reactivex.Single
import javax.inject.Inject

internal class GitHubRepositoryImpl @Inject constructor(private val api: Api) : GitHubRepository {
    override fun getGitHubRepos(
        page: Int,
        perPage: Int,
        query: String?
    ): Single<List<GitHubRepoDto>> {
        return api.getRepositories(page, perPage, query)
    }

    override fun getUser(userLogin: String): Single<UserDto> {
        return api.getUser(userLogin)
    }
}