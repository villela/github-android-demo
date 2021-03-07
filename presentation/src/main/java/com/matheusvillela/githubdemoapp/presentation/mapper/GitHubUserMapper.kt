package com.matheusvillela.githubdemoapp.presentation.mapper

import com.matheusvillela.githubdemoapp.data.dto.GitHubUserDto
import com.matheusvillela.githubdemoapp.presentation.domain.GitHubUser
import toothpick.InjectConstructor

@InjectConstructor
internal class GitHubUserMapper {
    fun toUser(userDto: GitHubUserDto): GitHubUser {
        return GitHubUser(userDto.name)
    }
}