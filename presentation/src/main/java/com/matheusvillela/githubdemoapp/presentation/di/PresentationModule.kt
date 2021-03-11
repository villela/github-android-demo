package com.matheusvillela.githubdemoapp.presentation.di

import com.matheusvillela.githubdemoapp.data.DataEnvironment
import com.matheusvillela.githubdemoapp.data.di.DataModule
import com.matheusvillela.githubdemoapp.presentation.repoinfo.RepoInfoViewModel
import com.matheusvillela.githubdemoapp.presentation.repoinfo.RepoInfoViewModelImpl
import com.matheusvillela.githubdemoapp.presentation.repolist.RepoListViewModel
import com.matheusvillela.githubdemoapp.presentation.repolist.RepoListViewModelImpl
import com.matheusvillela.githubdemoapp.presentation.shared.DataEnvironmentImpl
import com.matheusvillela.githubdemoapp.presentation.shared.OnClearedPublisher
import com.matheusvillela.githubdemoapp.presentation.shared.OnClearedSubscriber
import toothpick.config.Module

object PresentationModule : Module() {
    init {
        setup()
    }

    private fun setup() {
        bind(OnClearedSubscriber::class.java).singleton()
        bind(OnClearedPublisher::class.java).singleton()

        bind(RepoListViewModel::class.java).to(RepoListViewModelImpl::class.java).singleton()
        bind(RepoInfoViewModel::class.java).to(RepoInfoViewModelImpl::class.java).singleton()

        bind(DataEnvironment::class.java).to(DataEnvironmentImpl::class.java)

        bindingSet.addAll(DataModule.bindingSet)
    }
}