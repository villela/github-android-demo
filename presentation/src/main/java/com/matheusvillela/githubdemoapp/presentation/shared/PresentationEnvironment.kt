package com.matheusvillela.githubdemoapp.presentation.shared

import java.io.File

interface PresentationEnvironment {
    val isDebug: Boolean
    val gitHubBaseUrl: String
    val gitHubToken: String
    val cacheDir: File
}