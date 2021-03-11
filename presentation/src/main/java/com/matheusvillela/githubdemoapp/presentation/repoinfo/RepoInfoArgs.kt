package com.matheusvillela.githubdemoapp.presentation.repoinfo

import com.matheusvillela.githubdemoapp.presentation.domain.GitHubRepo
import java.io.Serializable

data class RepoInfoArgs(val repo: GitHubRepo) : Serializable