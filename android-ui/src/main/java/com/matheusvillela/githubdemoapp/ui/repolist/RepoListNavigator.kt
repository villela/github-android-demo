package com.matheusvillela.githubdemoapp.ui.repolist

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.matheusvillela.githubdemoapp.shared.NavigationContainer
import toothpick.InjectConstructor

@InjectConstructor
class RepoListNavigator(
    private val navigationContainer: NavigationContainer,
    private val fragmentManager: FragmentManager
) {
    fun navigate() {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(
            navigationContainer.layoutId,
            RepoListFragment::class.java, bundleOf()
        )
        transaction.addToBackStack(null).commit()
    }
}