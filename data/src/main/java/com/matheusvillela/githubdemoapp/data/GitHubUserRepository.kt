package com.matheusvillela.githubdemoapp.data

import com.matheusvillela.githubdemoapp.data.dto.GitHubUserDto
import io.reactivex.rxjava3.core.Single

interface GitHubUserRepository {
    fun getUser(userLogin: String): Single<GitHubUserDto>
}