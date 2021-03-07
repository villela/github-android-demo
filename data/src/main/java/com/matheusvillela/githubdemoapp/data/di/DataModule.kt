package com.matheusvillela.githubdemoapp.data.di

import com.matheusvillela.githubdemoapp.data.*
import retrofit2.Converter
import toothpick.config.Module

open class DataModule : Module() {
    init {
        setup()
    }

    private fun setup() {
        bind(Api::class.java).toProvider(ApiProvider::class.java)
        bind(Converter.Factory::class.java).toProvider(ConverterFactoryProvider::class.java)
        bind(GitHubRepoRepository::class.java).to(GitHubRepoRepositoryImpl::class.java)
        bind(GitHubUserRepository::class.java).to(GitHubUserRepositoryImpl::class.java)
    }
}