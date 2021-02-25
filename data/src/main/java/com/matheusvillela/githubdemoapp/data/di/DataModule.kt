package com.matheusvillela.githubdemoapp.data.di

import com.matheusvillela.githubdemoapp.data.Api
import com.matheusvillela.githubdemoapp.data.GitHubRepository
import com.matheusvillela.githubdemoapp.data.GitHubRepositoryImpl
import retrofit2.Converter
import toothpick.config.Module

class DataModule : Module() {
    init {
        bind(Api::class.java).toProvider(ApiProvider::class.java).providesSingleton()
        bind(Converter.Factory::class.java).toProvider(ConverterFactoryProvider::class.java)
            .providesSingleton()
        bind(GitHubRepository::class.java).to(GitHubRepositoryImpl::class.java)
    }
}