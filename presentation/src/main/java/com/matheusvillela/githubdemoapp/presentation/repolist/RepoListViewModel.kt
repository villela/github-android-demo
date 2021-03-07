package com.matheusvillela.githubdemoapp.presentation.repolist

import io.reactivex.rxjava3.core.Observable

interface RepoListViewModel {
    val state: Observable<RepoListState>

    fun searchReposByName(repoName: String)
}