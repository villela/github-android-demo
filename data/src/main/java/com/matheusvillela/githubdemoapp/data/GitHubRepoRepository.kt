package com.matheusvillela.githubdemoapp.data

import com.matheusvillela.githubdemoapp.data.dto.GitHubRepoDto
import com.matheusvillela.githubdemoapp.data.dto.GitHubRepoSearchContainerDto
import io.reactivex.rxjava3.core.Single

interface GitHubRepoRepository {
    fun searchGitHubReposByName(
        searchStr: String,
        sinceRepoId: Int? = null
    ): Single<GitHubRepoSearchContainerDto>

    fun getGitHubRepos(sinceRepoId: Int? = null): Single<List<GitHubRepoDto>>
}