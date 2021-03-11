package com.matheusvillela.githubdemoapp.ui.repolist

import com.matheusvillela.githubdemoapp.presentation.domain.GitHubRepo
import com.matheusvillela.githubdemoapp.shared.OnClickSubscriber
import toothpick.InjectConstructor

@InjectConstructor
class OnRepoClickSubscriber : OnClickSubscriber<GitHubRepo>()