package com.matheusvillela.githubdemoapp.data

import com.matheusvillela.githubdemoapp.data.di.ApiProvider
import com.matheusvillela.githubdemoapp.data.di.ConverterFactoryProvider
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test

class ApiTest {

    private lateinit var api: Api

    @Before
    fun setup() {
        val okHttpClient = OkHttpClient.Builder().build()
        val env = object : Environment {
            override val gitHubBaseUrl = "https://api.github.com/"
        }
        api = ApiProvider(env, okHttpClient, ConverterFactoryProvider().get()).get()
    }

    @Test
    fun test_get_repository_parses_and_is_not_empty() {
        val single = api.getRepositories(0)
        val list = single.blockingGet()
        assert(list.isNotEmpty())
    }

    @Test
    fun test_get_user_villela_should_parse_and_return_correct_name() {
        val single = api.getUser("villela")
        val user = single.blockingGet()
        assert(user.name == "Matheus Villela")
    }
}