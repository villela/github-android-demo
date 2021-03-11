package com.matheusvillela.githubdemoapp.di

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.matheusvillela.githubdemoapp.presentation.di.PresentationModule
import com.matheusvillela.githubdemoapp.presentation.shared.OnClearedPublisher
import toothpick.Scope
import toothpick.config.Module
import toothpick.ktp.KTP
import java.io.Serializable
import java.util.*

class GitHubFragmentFactory(private val rootScreenScope: Scope, private val activityModule: Module) :
    FragmentFactory() {
    companion object {
        const val ARGUMENT_DEFAULT_KEY = "ARGUMENT_DEFAULT_KEY"
        const val FRAGMENT_UUID_KEY = "FRAGMENT_UUID_KEY"
    }

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        throw Exception("should not be used in the context of this app")
    }

    override fun instantiateWithArguments(
        classLoader: ClassLoader,
        className: String,
        arguments: Bundle?
    ): Fragment {
        return createFragment(classLoader, className, arguments)
    }

    private fun createFragment(
        classLoader: ClassLoader, className: String, arguments: Bundle?
    ): Fragment {
        val args = arguments ?: return instantiate(classLoader, className)
        val cls = loadFragmentClass(classLoader, className)
        val identifier = args.getString(FRAGMENT_UUID_KEY)
            ?: UUID.randomUUID().toString()
        args.putString(FRAGMENT_UUID_KEY, identifier)
        val screenScope = rootScreenScope.openSubScope(identifier) {
            it.installModules(object : Module() {
                init {
                    val obj: Serializable? = args.getSerializable(ARGUMENT_DEFAULT_KEY)
                    if (obj != null) {
                        bind(obj.javaClass).toInstance(obj)
                    }
                }
            }, PresentationModule)
        }
        val publisher = screenScope.getInstance(OnClearedPublisher::class.java)
        val fragmentViewScope = screenScope.openSubScope(UUID.randomUUID().toString()) {
            it.installModules(FragmentsModule, activityModule)
        }
        val fragment = fragmentViewScope.getInstance(cls)
        fragment.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                KTP.closeScope(fragmentViewScope.name)
                if (fragment.isRemoving) {
                    KTP.closeScope(screenScope.name)
                    fragment.lifecycle.removeObserver(this)
                    publisher.publish()
                }
            }
        })
        return fragment
    }
}