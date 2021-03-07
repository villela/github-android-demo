package com.matheusvillela.githubdemoapp.presentation.di

import com.matheusvillela.githubdemoapp.data.di.DataModule
import com.matheusvillela.githubdemoapp.presentation.repolist.RepoListViewModel
import com.matheusvillela.githubdemoapp.presentation.repolist.RepoListViewModelImpl
import com.matheusvillela.githubdemoapp.presentation.shared.OnClearedPublisher
import com.matheusvillela.githubdemoapp.presentation.shared.OnClearedSubscriber

open class PresentationModule : DataModule() {
    init {
        setup()
    }

    private fun setup() {
        bind(OnClearedSubscriber::class.java).singleton()
        bind(OnClearedPublisher::class.java).singleton()

        bind(RepoListViewModel::class.java).to(RepoListViewModelImpl::class.java)
    }
}