package com.matheusvillela.githubdemoapp.data.di

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import toothpick.InjectConstructor
import java.util.concurrent.TimeUnit


@InjectConstructor
class ForceCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        val response = chain.proceed(chain.request())

        val cacheControl = CacheControl.Builder()
            .maxAge(1, TimeUnit.HOURS)
            .build()

        return response.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}
