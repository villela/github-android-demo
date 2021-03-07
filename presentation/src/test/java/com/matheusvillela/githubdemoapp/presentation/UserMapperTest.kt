package com.matheusvillela.githubdemoapp.presentation

import com.matheusvillela.githubdemoapp.data.dto.GitHubUserDto
import com.matheusvillela.githubdemoapp.presentation.mapper.GitHubUserMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserMapperTest {

    private lateinit var dto: GitHubUserDto
    private lateinit var mapper: GitHubUserMapper

    @BeforeEach
    fun setup() {
        mapper = GitHubUserMapper()
        dto = GitHubUserDto("name")
    }

    @Test
    fun test_name_maps_correctly() {
        val result = mapper.toUser(dto)
        assert(result.name == "name")
    }
}