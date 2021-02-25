package com.matheusvillela.githubdemoapp.data.di

import com.matheusvillela.githubdemoapp.data.Api
import com.matheusvillela.githubdemoapp.data.Environment
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Inject
import javax.inject.Provider

internal class ApiProvider @Inject constructor(
    private val environment: Environment,
    private val client: OkHttpClient,
    private val converterFactory: Converter.Factory
) : Provider<Api> {
    override fun get(): Api {
        return Retrofit.Builder()
            .baseUrl(environment.gitHubBaseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(converterFactory)
            .build().create(Api::class.java)
    }
}