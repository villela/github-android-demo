package com.matheusvillela.githubdemoapp.data

import com.matheusvillela.githubdemoapp.data.di.ApiProvider
import com.matheusvillela.githubdemoapp.data.di.ConverterFactoryProvider
import okhttp3.OkHttpClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class ApiTest {

    private lateinit var api: Api

    @BeforeEach
    fun setup() {
        val okHttpClient = OkHttpClient.Builder().build()
        val env = object : Environment {
            override val gitHubBaseUrl = "https://api.github.com/"
        }
        api = ApiProvider(env, okHttpClient, ConverterFactoryProvider().get()).get()
    }

    @Test
    fun test_get_repository_parses_and_is_not_empty() {
        val dto = api.getRepositories().blockingGet()
        assert(dto.isNotEmpty())
    }

    @Test
    fun test_get_repository_pages_works_as_expected() {
        val firstDto = api.getRepositories().blockingGet()
        assert(firstDto.isNotEmpty())

        val secondDto = api.getRepositories(firstDto.maxByOrNull { it.id }?.id).blockingGet()
        assert(secondDto.isNotEmpty())

        val firstRequestLastItemId = firstDto.last().id
        val secondRequestFirstItemId = secondDto.first().id
        assert(firstRequestLastItemId < secondRequestFirstItemId)
    }

    @Test
    fun test_get_user_villela_should_parse_and_return_correct_name() {
        val dto = api.getUser("villela").blockingGet()
        assert(dto.name == "Matheus Villela")
    }

    @Test
    fun test_get_unknown_user_should_throw_exception() {
        try {
            api.getUser(UUID.randomUUID().toString()).blockingGet()
            assert(false) { "getting non existent user should throw exception" }
        } catch (ex: Exception) {
        }
    }

    @Test
    fun test_search_repository() {
        val container = api.searchRepositories("retrofit in:name").blockingGet()
        assert(container.totalCount > 0)
    }
}