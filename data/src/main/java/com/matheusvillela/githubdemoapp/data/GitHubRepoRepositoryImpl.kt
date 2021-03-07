package com.matheusvillela.githubdemoapp.data

import com.matheusvillela.githubdemoapp.data.dto.GitHubRepoDto
import com.matheusvillela.githubdemoapp.data.dto.GitHubRepoSearchContainerDto
import io.reactivex.rxjava3.core.Single
import toothpick.InjectConstructor

@InjectConstructor
internal class GitHubRepoRepositoryImpl(
    private val api: Api
) : GitHubRepoRepository {

    override fun searchGitHubReposByName(
        searchStr: String,
        sinceRepoId: Int?
    ): Single<GitHubRepoSearchContainerDto> {
        return api.searchRepositories("$searchStr in:name", sinceRepoId)
    }

    override fun getGitHubRepos(
        sinceRepoId: Int?
    ): Single<List<GitHubRepoDto>> {
        return api.getRepositories(sinceRepoId)
    }
}