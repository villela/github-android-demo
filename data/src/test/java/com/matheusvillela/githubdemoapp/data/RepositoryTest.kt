package com.matheusvillela.githubdemoapp.data

import com.matheusvillela.githubdemoapp.data.di.ApiProvider
import com.matheusvillela.githubdemoapp.data.di.ConverterFactoryProvider
import com.matheusvillela.githubdemoapp.data.dto.UserDto
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test

class RepositoryTest {

    private lateinit var repository: GitHubRepository

    @Before
    fun setup() {
        val okHttpClient = OkHttpClient.Builder().build()
        val env = object : Environment {
            override val gitHubBaseUrl = "https://api.github.com/"
        }
        val api = ApiProvider(env, okHttpClient, ConverterFactoryProvider().get()).get()
        repository = GitHubRepositoryImpl(api)
    }

    @Test
    fun test_mocked_api_repository_returns_correct_user() {
        val api = mockk<Api>()
        every { api.getUser("test") } returns Single.create { emitter -> emitter.onSuccess(UserDto("testUser")) }
        val customRepo = GitHubRepositoryImpl(api)

        val single = customRepo.getUser("test")
        val user = single.blockingGet()
        assert(user.name == "testUser")
    }

    @Test
    fun test_get_repositories_parses_and_is_not_empty() {
        val single = repository.getGitHubRepos(0, 10)
        val list = single.blockingGet()
        assert(list.isNotEmpty())
    }

    @Test
    fun test_get_user_villela_should_parse_and_return_correct_name() {
        val single = repository.getUser("villela")
        val user = single.blockingGet()
        assert(user.name == "Matheus Villela")
    }
}