package com.matheusvillela.githubdemoapp

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import toothpick.InjectConstructor

@InjectConstructor
class BackNavigator(
    private val fragmentManager: FragmentManager,
    private val activity: AppCompatActivity
) {
    fun navigate() {
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStackImmediate()
        } else {
            activity.finish()
        }
    }
}