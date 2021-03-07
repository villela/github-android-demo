package com.matheusvillela.githubdemoapp.data

import com.matheusvillela.githubdemoapp.data.dto.GitHubUserDto
import io.reactivex.rxjava3.core.Single
import toothpick.InjectConstructor

@InjectConstructor
internal class GitHubUserRepositoryImpl(
    private val api: Api
) : GitHubUserRepository {
    override fun getUser(userLogin: String): Single<GitHubUserDto> {
        return api.getUser(userLogin)
    }
}