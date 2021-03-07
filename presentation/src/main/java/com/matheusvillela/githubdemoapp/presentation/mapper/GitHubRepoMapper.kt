package com.matheusvillela.githubdemoapp.presentation.mapper

import com.matheusvillela.githubdemoapp.data.dto.GitHubRepoDto
import com.matheusvillela.githubdemoapp.presentation.domain.GitHubRepo
import toothpick.InjectConstructor

@InjectConstructor
internal class GitHubRepoMapper {
    fun toGitHubRepo(gitHubRepoDto: GitHubRepoDto): GitHubRepo {
        return GitHubRepo(
            gitHubRepoDto.id, gitHubRepoDto.name, gitHubRepoDto.description,
            GitHubRepo.RepositoryOwner(gitHubRepoDto.owner.login)
        )
    }
}