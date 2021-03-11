package com.matheusvillela.githubdemoapp.di

import com.matheusvillela.githubdemoapp.ui.repolist.OnRepoClickPublisher
import com.matheusvillela.githubdemoapp.ui.repolist.OnRepoClickSubscriber
import toothpick.config.Module

object FragmentsModule : Module() {
    init {
        setup()
    }

    private fun setup() {
        bind(OnRepoClickSubscriber::class.java).singleton()
        bind(OnRepoClickPublisher::class.java).singleton()
    }
}