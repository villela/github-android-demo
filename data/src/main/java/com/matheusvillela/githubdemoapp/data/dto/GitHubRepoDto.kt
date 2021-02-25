package com.matheusvillela.githubdemoapp.data.dto

data class GitHubRepoDto(
    val id: Int,
    val name: String,
    val description: String?,
    val owner: RepositoryOwnerDto
) {
    data class RepositoryOwnerDto(
        val login: String
    )
}