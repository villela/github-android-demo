package com.matheusvillela.githubdemoapp.data.di

import com.matheusvillela.githubdemoapp.data.Api
import com.matheusvillela.githubdemoapp.data.Environment
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
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
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(converterFactory)
            .build().create(Api::class.java)
    }
}