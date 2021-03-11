package com.matheusvillela.githubdemoapp.presentation.repoinfo

import com.matheusvillela.githubdemoapp.presentation.domain.GitHubRepo
import com.matheusvillela.githubdemoapp.presentation.domain.GitHubUser

sealed class RepoInfoState(
    val repo: GitHubRepo
) {
    class Loading(repo: GitHubRepo) : RepoInfoState(repo)
    class Success(repo: GitHubRepo, val user: GitHubUser) : RepoInfoState(repo)
    class Error(repo: GitHubRepo, val throwable: Throwable) : RepoInfoState(repo)
}