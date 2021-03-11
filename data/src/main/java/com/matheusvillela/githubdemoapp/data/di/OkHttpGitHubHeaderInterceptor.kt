package com.matheusvillela.githubdemoapp.data.di

import com.matheusvillela.githubdemoapp.data.DataEnvironment
import okhttp3.Interceptor
import okhttp3.Response
import toothpick.InjectConstructor

@InjectConstructor
class OkHttpGitHubHeaderInterceptor(private val environment: DataEnvironment) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .header("Authorization", "token ${environment.gitHubToken}")
            .build()
    }
}
