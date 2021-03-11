package com.matheusvillela.githubdemoapp.data.dto

import com.squareup.moshi.Json

data class GitHubUserDto(
    val name: String?,
    @Json(name = "avatar_url")
    val avatarUrl: String
)