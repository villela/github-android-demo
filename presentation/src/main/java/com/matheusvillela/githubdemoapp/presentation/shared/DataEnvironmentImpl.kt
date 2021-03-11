package com.matheusvillela.githubdemoapp.presentation.shared

import com.matheusvillela.githubdemoapp.data.DataEnvironment
import toothpick.InjectConstructor

@InjectConstructor
internal class DataEnvironmentImpl(presentationEnvironment: PresentationEnvironment) :
    DataEnvironment {
    override val isDebug = presentationEnvironment.isDebug
    override val gitHubBaseUrl = presentationEnvironment.gitHubBaseUrl
    override val gitHubToken = presentationEnvironment.gitHubToken
    override val cacheDir = presentationEnvironment.cacheDir
}