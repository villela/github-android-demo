package com.matheusvillela.githubdemoapp.presentation

import com.matheusvillela.githubdemoapp.data.GitHubUserRepository
import com.matheusvillela.githubdemoapp.data.dto.GitHubUserDto
import com.matheusvillela.githubdemoapp.presentation.domain.GitHubRepo
import com.matheusvillela.githubdemoapp.presentation.mapper.GitHubUserMapper
import com.matheusvillela.githubdemoapp.presentation.repoinfo.RepoInfoArgs
import com.matheusvillela.githubdemoapp.presentation.repoinfo.RepoInfoState
import com.matheusvillela.githubdemoapp.presentation.repoinfo.RepoInfoViewModel
import com.matheusvillela.githubdemoapp.presentation.repoinfo.RepoInfoViewModelImpl
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
class RepoInfoViewModelTest {

    private lateinit var viewModel: RepoInfoViewModel

    @BeforeEach
    fun setup() {
        val subs = mockk<OnClearedSubscriber>()
        every { subs.onCleared() } just Runs
        every { subs.subscribe(*any()) } just Runs
        val repo = mockk<GitHubUserRepository>()
        every { repo.getUser(*any()) } returns Single.create { emitter ->
            emitter.onSuccess(
                GitHubUserDto("userName")
            )
        }
        val mapper = GitHubUserMapper()
        viewModel = RepoInfoViewModelImpl(
            RepoInfoArgs(
                (GitHubRepo(
                    1,
                    "repoName",
                    "desc",
                    GitHubRepo.RepositoryOwner("login")
                ))
            ), subs, repo, mapper
        )
    }

    @Test
    fun test_repo_info_is_correct() {
        val state = viewModel.state.blockingFirst()
        assert(state.repo.name == "repoName")
        assert(state.repo.description == "desc")
        assert(state.repo.owner.login == "login")
    }

    @Test
    fun test_user_info_is_correct() {
        val state = viewModel.state.blockingFirst()
        assert(state is RepoInfoState.Success && state.user.name == "userName")
    }

}