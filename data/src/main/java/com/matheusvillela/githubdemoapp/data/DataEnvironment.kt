package com.matheusvillela.githubdemoapp.data

import java.io.File

interface DataEnvironment {
    val isDebug: Boolean
    val gitHubBaseUrl: String
    val gitHubToken: String
    val cacheDir: File
}