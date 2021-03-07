package com.matheusvillela.githubdemoapp.presentation.repolist

import com.matheusvillela.githubdemoapp.data.GitHubRepoRepository
import com.matheusvillela.githubdemoapp.presentation.mapper.GitHubRepoMapper
import com.matheusvillela.githubdemoapp.presentation.shared.OnClearedSubscriber
import com.matheusvillela.githubdemoapp.presentation.shared.addTo
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import toothpick.InjectConstructor

@InjectConstructor
internal class RepoListViewModelImpl(
    onClearedSubscriber: OnClearedSubscriber,
    private val repoRepository: GitHubRepoRepository,
    private val repoMapper: GitHubRepoMapper
) : RepoListViewModel {
    override val state: BehaviorSubject<RepoListState> =
        BehaviorSubject.createDefault(RepoListState.Loading)

    private val disposables = CompositeDisposable()

    init {
        onClearedSubscriber.subscribe { disposables.dispose() }
        searchReposByName("")
    }

    override fun searchReposByName(repoName: String) {
        state.onNext(RepoListState.Loading)
        if (repoName.isBlank()) {
            repoRepository.getGitHubRepos()
                .subscribeOn(Schedulers.io())
                .subscribe({ dto ->
                    state.onNext(RepoListState.Success(dto.map(repoMapper::toGitHubRepo)))
                }, { state.onNext(RepoListState.Error) })
                .addTo(disposables)
        } else {
            repoRepository.searchGitHubReposByName(repoName)
                .subscribeOn(Schedulers.io())
                .subscribe({ dto ->
                    state.onNext(RepoListState.Success(dto.items.map(repoMapper::toGitHubRepo)))
                }, { state.onNext(RepoListState.Error) })
                .addTo(disposables)
        }
    }
}