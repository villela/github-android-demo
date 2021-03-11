package com.matheusvillela.githubdemoapp.di

import android.app.Application
import com.matheusvillela.githubdemoapp.presentation.shared.PresentationEnvironment
import com.matheusvillela.githubdemoapp.shared.PresentationEnvironmentImpl
import toothpick.config.Module

class AppModule(application: Application) : Module() {

    init {
        bind(Application::class.java).toInstance(application)
        bind(PresentationEnvironment::class.java).to(PresentationEnvironmentImpl::class.java)
    }
}