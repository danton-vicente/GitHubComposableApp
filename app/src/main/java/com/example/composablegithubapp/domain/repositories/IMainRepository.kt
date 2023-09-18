package com.example.composablegithubapp.domain.repositories

import com.example.composablegithubapp.domain.model.GitHubResponse

interface IMainRepository {

    suspend fun getResults(page: Int = 1): Result<GitHubResponse>
}
