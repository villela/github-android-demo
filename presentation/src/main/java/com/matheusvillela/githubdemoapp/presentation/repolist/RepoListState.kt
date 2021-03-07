package com.matheusvillela.githubdemoapp.presentation.repolist

import com.matheusvillela.githubdemoapp.presentation.domain.GitHubRepo

sealed class RepoListState {
    object Loading : RepoListState()
    class Success(val repos: List<GitHubRepo>) : RepoListState()
    object Error : RepoListState()
}