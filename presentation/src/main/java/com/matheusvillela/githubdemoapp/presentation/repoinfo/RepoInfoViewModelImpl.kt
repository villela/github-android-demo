package com.matheusvillela.githubdemoapp.presentation.repoinfo

import com.matheusvillela.githubdemoapp.data.GitHubUserRepository
import com.matheusvillela.githubdemoapp.presentation.mapper.GitHubUserMapper
import com.matheusvillela.githubdemoapp.presentation.shared.OnClearedSubscriber
import com.matheusvillela.githubdemoapp.presentation.shared.addTo
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import toothpick.InjectConstructor

@InjectConstructor
internal class RepoInfoViewModelImpl(
    args: RepoInfoArgs,
    onClearedSubscriber: OnClearedSubscriber,
    private val gitHubUserRepository: GitHubUserRepository,
    private val userMapper: GitHubUserMapper
) : RepoInfoViewModel {
    override val state: BehaviorSubject<RepoInfoState> =
        BehaviorSubject.createDefault(RepoInfoState.Loading(args.repo))

    private val disposables = CompositeDisposable()

    init {
        onClearedSubscriber.subscribe { disposables.dispose() }
        loadRepoInfo()
    }

    override fun reloadRepoInfo() {
        if (state.value !is RepoInfoState.Loading) {
            loadRepoInfo()
        }
    }

    private fun loadRepoInfo() {
        val repo = state.value.repo
        state.onNext(RepoInfoState.Loading(repo))
        gitHubUserRepository.getUser(repo.owner.login)
            .subscribeOn(Schedulers.io())
            .subscribe({ dto ->
                state.onNext(RepoInfoState.Success(repo, userMapper.toUser(dto)))
            }, { state.onNext(RepoInfoState.Error(repo, it)) })
            .addTo(disposables)
    }
}