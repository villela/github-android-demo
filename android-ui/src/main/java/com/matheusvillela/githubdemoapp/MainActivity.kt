package com.matheusvillela.githubdemoapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.matheusvillela.githubdemoapp.di.GitHubFragmentFactory
import com.matheusvillela.githubdemoapp.shared.NavigationContainer
import com.matheusvillela.githubdemoapp.ui.repolist.RepoListNavigator
import toothpick.Scope
import toothpick.config.Module
import toothpick.ktp.KTP
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var screenScope: Scope
    private lateinit var viewScope: Scope

    companion object {
        private const val EXTRAS_SCREEN_SCOPE_UUID_KEY = "EXTRAS_SCREEN_SCOPE_UUID_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenScopeUuid =
            savedInstanceState?.getString(EXTRAS_SCREEN_SCOPE_UUID_KEY) ?: UUID.randomUUID()
                .toString()
        screenScope = KTP.openRootScope()
            .openSubScope(screenScopeUuid)
        val viewScopeUuid = UUID.randomUUID().toString()
        viewScope = screenScope.openSubScope(viewScopeUuid)
        val activityModule = object : Module() {
            init {
                bind(Context::class.java).toInstance(this@MainActivity)
                bind(AppCompatActivity::class.java).toInstance(this@MainActivity)
                bind(FragmentManager::class.java).toInstance(supportFragmentManager)
                bind(NavigationContainer::class.java).toInstance(NavigationContainer(R.id.container))
            }
        }
        viewScope.installModules(activityModule)
        supportFragmentManager.fragmentFactory = GitHubFragmentFactory(screenScope, activityModule)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val navigator = viewScope.getInstance(RepoListNavigator::class.java)
            navigator.navigate()
        }
    }

    override fun onBackPressed() {
        val nav = viewScope.getInstance(BackNavigator::class.java)
        nav.navigate()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EXTRAS_SCREEN_SCOPE_UUID_KEY, screenScope.name as String)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        KTP.closeScope(viewScope.name)
        if (isFinishing) {
            KTP.closeScope(screenScope.name)
        }
        super.onDestroy()
    }
}