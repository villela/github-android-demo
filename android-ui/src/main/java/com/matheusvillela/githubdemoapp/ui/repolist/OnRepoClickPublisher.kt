package com.matheusvillela.githubdemoapp.ui.repolist

import com.matheusvillela.githubdemoapp.presentation.domain.GitHubRepo
import com.matheusvillela.githubdemoapp.shared.OnClickPublisher
import toothpick.InjectConstructor

@InjectConstructor
class OnRepoClickPublisher(subscriber: OnRepoClickSubscriber) :
    OnClickPublisher<GitHubRepo>(subscriber)