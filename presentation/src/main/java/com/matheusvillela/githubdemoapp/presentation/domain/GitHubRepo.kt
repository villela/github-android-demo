package com.matheusvillela.githubdemoapp.presentation.domain

data class GitHubRepo(
    val id: Int,
    val name: String,
    val description: String?,
    val owner: RepositoryOwner
) {
    data class RepositoryOwner(
        val login: String
    )
}