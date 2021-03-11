package com.matheusvillela.githubdemoapp.shared

import android.app.Application
import com.matheusvillela.githubdemoapp.BuildConfig
import com.matheusvillela.githubdemoapp.presentation.shared.PresentationEnvironment
import toothpick.InjectConstructor
import java.io.File

@InjectConstructor
class PresentationEnvironmentImpl(application: Application) : PresentationEnvironment {
    override val isDebug = BuildConfig.DEBUG
    override val gitHubBaseUrl = BuildConfig.API_URL
    override val gitHubToken = BuildConfig.GITHUB_TOKEN
    override val cacheDir: File = application.cacheDir
}