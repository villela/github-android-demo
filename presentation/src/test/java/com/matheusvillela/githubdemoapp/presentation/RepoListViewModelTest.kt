package com.matheusvillela.githubdemoapp.presentation

import com.matheusvillela.githubdemoapp.data.GitHubRepoRepository
import com.matheusvillela.githubdemoapp.data.dto.GitHubRepoDto
import com.matheusvillela.githubdemoapp.data.dto.GitHubRepoSearchContainerDto
import com.matheusvillela.githubdemoapp.presentation.mapper.GitHubRepoMapper
import com.matheusvillela.githubdemoapp.presentation.repolist.RepoListState
import com.matheusvillela.githubdemoapp.presentation.repolist.RepoListViewModel
import com.matheusvillela.githubdemoapp.presentation.repolist.RepoListViewModelImpl
import com.matheusvillela.githubdemoapp.presentation.shared.OnClearedSubscriber
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepoListViewModelTest {

    private lateinit var viewModel: RepoListViewModel

    @BeforeEach
    fun setup() {
        val subs = mockk<OnClearedSubscriber>()
        every { subs.onCleared() } just Runs
        every { subs.subscribe(*any()) } just Runs
        val repo = mockk<GitHubRepoRepository>()
        every { repo.getGitHubRepos() } returns Single.create { emitter ->
            emitter.onSuccess(
                listOf(
                    GitHubRepoDto(1, "name", "desc", GitHubRepoDto.RepositoryOwnerDto("login"))
                )
            )
        }

        every { repo.searchGitHubReposByName(*any()) } returns Single.create { emitter ->
            emitter.onSuccess(
                GitHubRepoSearchContainerDto(
                    1, listOf(
                        GitHubRepoDto(
                            1,
                            "searchName",
                            "searchDesc",
                            GitHubRepoDto.RepositoryOwnerDto("searchLogin")
                        )
                    )
                )
            )
        }
        val mapper = GitHubRepoMapper()
        viewModel = RepoListViewModelImpl(subs, repo, mapper)
    }

    @Test
    fun default_list_should_return_item_after_one_second() {
        val state = viewModel.state.blockingFirst()
        assert(state is RepoListState.Success && state.repos.size == 1 && state.repos[0].name == "name" && state.repos[0].description == "desc" && state.repos[0].owner.login == "login")
    }

    @Test
    fun searched_list_should_return_searched_item_after_one_second() {
        viewModel.searchReposByName("bla")
        val state = viewModel.state.blockingFirst()
        assert(state is RepoListState.Success && state.repos.size == 1 && state.repos[0].name == "searchName" && state.repos[0].description == "searchDesc" && state.repos[0].owner.login == "searchLogin")
    }

}