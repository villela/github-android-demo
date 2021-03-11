package com.matheusvillela.githubdemoapp.data

import com.matheusvillela.githubdemoapp.data.di.ApiProvider
import com.matheusvillela.githubdemoapp.data.di.ConverterFactoryProvider
import okhttp3.OkHttpClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class RepoRepositoryTest {

    private lateinit var repository: GitHubRepoRepository

    @BeforeEach
    fun setup() {
        val okHttpClient = OkHttpClient.Builder().build()
        val env = object : DataEnvironment {
            override val gitHubBaseUrl = "https://api.github.com/"
            override val gitHubToken = ""
            override val cacheDir = File("")
            override val isDebug = true
        }
        val api = ApiProvider(env, okHttpClient, ConverterFactoryProvider().get()).get()
        repository = GitHubRepoRepositoryImpl(api)
    }

    @Test
    fun test_get_repositories_parses_and_is_not_empty() {
        val dto = repository.getGitHubRepos().blockingGet()
        assert(dto.isNotEmpty())
    }

    @Test
    fun test_search_repo_retrofit_returns_payload() {
        val dto = repository.searchGitHubReposByName("retrofit").blockingGet()
        assert(dto.totalCount > 0)
    }
}