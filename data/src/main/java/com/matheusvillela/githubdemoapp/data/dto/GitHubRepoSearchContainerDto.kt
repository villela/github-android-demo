package com.matheusvillela.githubdemoapp.data.dto

import com.squareup.moshi.Json

data class GitHubRepoSearchContainerDto(
    @Json(name="total_count")
    val totalCount: Int,
    val items: List<GitHubRepoDto>
)