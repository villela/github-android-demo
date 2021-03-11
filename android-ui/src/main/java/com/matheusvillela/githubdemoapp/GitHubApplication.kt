package com.matheusvillela.githubdemoapp

import android.app.Application
import com.matheusvillela.githubdemoapp.di.AppModule
import toothpick.ktp.KTP

class GitHubApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KTP.openRootScope()
            .installModules(AppModule(this))
    }
}