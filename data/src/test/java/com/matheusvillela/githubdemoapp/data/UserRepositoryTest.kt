package com.matheusvillela.githubdemoapp.data

import com.matheusvillela.githubdemoapp.data.di.ApiProvider
import com.matheusvillela.githubdemoapp.data.di.ConverterFactoryProvider
import com.matheusvillela.githubdemoapp.data.dto.GitHubUserDto
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class UserRepositoryTest {

    private lateinit var repository: GitHubUserRepository

    @BeforeEach
    fun setup() {
        val okHttpClient = OkHttpClient.Builder().build()
        val env = object : DataEnvironment {
            override val gitHubBaseUrl = "https://api.github.com/"
            override val isDebug = true
            override val gitHubToken = ""
            override val cacheDir = File("")
        }
        val api = ApiProvider(env, okHttpClient, ConverterFactoryProvider().get()).get()
        repository = GitHubUserRepositoryImpl(api)
    }

    @Test
    fun test_get_user_villela_should_parse_and_return_correct_name() {
        val dto = repository.getUser("villela").blockingGet()
        assert(dto.name == "Matheus Villela")
    }


    @Test
    fun test_mocked_api_repository_returns_correct_user() {
        val api = mockk<Api>()
        every { api.getUser(*any()) } returns Single.create { emitter ->
            emitter.onSuccess(
                GitHubUserDto("testUser", "https://test")
            )
        }
        val customRepo = GitHubUserRepositoryImpl(api)

        val dto = customRepo.getUser("").blockingGet()
        assert(dto.name == "testUser")
        assert(dto.avatarUrl == "https://test")
    }
}