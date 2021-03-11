package com.matheusvillela.githubdemoapp.ui.repoinfo

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.matheusvillela.githubdemoapp.di.GitHubFragmentFactory
import com.matheusvillela.githubdemoapp.presentation.domain.GitHubRepo
import com.matheusvillela.githubdemoapp.presentation.repoinfo.RepoInfoArgs
import com.matheusvillela.githubdemoapp.shared.NavigationContainer
import toothpick.InjectConstructor

@InjectConstructor
class RepoInfoNavigator(
    private val navigationContainer: NavigationContainer,
    private val fragmentManager: FragmentManager
) {
    fun navigate(gitHubRepo: GitHubRepo) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(
            navigationContainer.layoutId,
            RepoInfoFragment::class.java, bundleOf(
                GitHubFragmentFactory.ARGUMENT_DEFAULT_KEY to RepoInfoArgs(
                    gitHubRepo
                )
            )
        )
        transaction.addToBackStack(null).commit()
    }
}