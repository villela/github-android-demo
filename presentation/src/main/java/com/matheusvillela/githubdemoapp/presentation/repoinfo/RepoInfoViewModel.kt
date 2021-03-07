package com.matheusvillela.githubdemoapp.presentation.repoinfo

import io.reactivex.rxjava3.core.Observable

interface RepoInfoViewModel {
    val state: Observable<RepoInfoState>

    fun reloadRepoInfo()
}