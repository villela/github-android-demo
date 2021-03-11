package com.matheusvillela.githubdemoapp.ui.repolist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding4.widget.textChangeEvents
import com.matheusvillela.githubdemoapp.R
import com.matheusvillela.githubdemoapp.databinding.FragmentRepoListBinding
import com.matheusvillela.githubdemoapp.presentation.domain.GitHubRepo
import com.matheusvillela.githubdemoapp.presentation.repolist.RepoListState
import com.matheusvillela.githubdemoapp.presentation.repolist.RepoListViewModel
import com.matheusvillela.githubdemoapp.shared.OnClickSubscriber
import com.matheusvillela.githubdemoapp.shared.addTo
import com.matheusvillela.githubdemoapp.ui.repoinfo.RepoInfoNavigator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import toothpick.InjectConstructor
import java.util.concurrent.TimeUnit

@InjectConstructor
class RepoListFragment(
    private val viewModel: RepoListViewModel,
    private val onClickSubscriber: OnRepoClickSubscriber,
    private val repoListAdapter: RepoListAdapter,
    private val repoInfoNavigator: RepoInfoNavigator
) : Fragment(R.layout.fragment_repo_list), OnClickSubscriber.Subscriber<GitHubRepo> {

    companion object {
        private const val SEARCH_DEBOUNCE_INTERVAL = 500L
    }

    private var disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onClickSubscriber.subscribe(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRepoListBinding.bind(view)
        binding.recycler.adapter = repoListAdapter
        viewModel.state.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.search.setTextKeepState(it.searchedValue)
                binding.loading.visibility = View.INVISIBLE
                when (it) {
                    is RepoListState.Success -> repoListAdapter.updateRepos(it.repos)
                    is RepoListState.Error -> { // TODO
                    }
                    is RepoListState.Loading -> binding.loading.visibility = View.VISIBLE
                }
            }.addTo(disposables)

        binding.search.textChangeEvents()
            .skipInitialValue()
            .debounce(SEARCH_DEBOUNCE_INTERVAL, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.searchRepos(it.text.toString())
            }.addTo(disposables)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.dispose()
        disposables = CompositeDisposable()
    }

    override fun onClick(obj: GitHubRepo) {
        repoInfoNavigator.navigate(obj)
    }
}