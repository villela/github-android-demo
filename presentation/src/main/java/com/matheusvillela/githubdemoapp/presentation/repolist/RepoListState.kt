package com.matheusvillela.githubdemoapp.presentation.repolist

import com.matheusvillela.githubdemoapp.presentation.domain.GitHubRepo

sealed class RepoListState(val searchedValue: String) {
    class Loading(searchedValue: String) : RepoListState(searchedValue)
    class Success(val repos: List<GitHubRepo>, searchedValue: String) : RepoListState(searchedValue)
    class Error(val throwable: Throwable, searchedValue: String) : RepoListState(searchedValue)
}