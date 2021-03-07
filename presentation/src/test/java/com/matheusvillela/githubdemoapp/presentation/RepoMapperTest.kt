package com.matheusvillela.githubdemoapp.presentation

import com.matheusvillela.githubdemoapp.data.dto.GitHubRepoDto
import com.matheusvillela.githubdemoapp.presentation.mapper.GitHubRepoMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RepoMapperTest {

    private lateinit var dto: GitHubRepoDto
    private lateinit var mapper: GitHubRepoMapper

    @BeforeEach
    fun setup() {
        mapper = GitHubRepoMapper()
        dto = GitHubRepoDto(1, "name", "desc", (GitHubRepoDto.RepositoryOwnerDto("login")))
    }

    @Test
    fun test_name_maps_correctly() {
        val result = mapper.toGitHubRepo(dto)
        assert(result.name == "name")
    }

    @Test
    fun test_id_maps_correctly() {
        val result = mapper.toGitHubRepo(dto)
        assert(result.id == 1)
    }

    @Test
    fun test_desc_maps_correctly() {
        val result = mapper.toGitHubRepo(dto)
        assert(result.description == "desc")
    }

    @Test
    fun test_owner_login_maps_correctly() {
        val result = mapper.toGitHubRepo(dto)
        assert(result.owner.login == "login")
    }
}