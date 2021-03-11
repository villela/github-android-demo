package com.matheusvillela.githubdemoapp.data.di

import com.matheusvillela.githubdemoapp.data.DataEnvironment
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import toothpick.InjectConstructor
import java.io.File
import javax.inject.Provider

@InjectConstructor
class OkHttpProvider(
    private val loggingInterceptor: HttpLoggingInterceptor,
    private val environment: DataEnvironment,
    private val okHttpGitHubHeaderInterceptor: OkHttpGitHubHeaderInterceptor,
    private val forceCacheInterceptor: ForceCacheInterceptor
) :
    Provider<OkHttpClient> {
    override fun get(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (environment.isDebug) {
            builder.addInterceptor(loggingInterceptor)
            builder.cache(
                Cache(
                    File(environment.cacheDir, "http_cache"), 50L * 1024L * 1024L // 50 MiB
                )
            )
            builder.addInterceptor(forceCacheInterceptor)
        }
        builder.addInterceptor(okHttpGitHubHeaderInterceptor)
        return builder.build()
    }
}
