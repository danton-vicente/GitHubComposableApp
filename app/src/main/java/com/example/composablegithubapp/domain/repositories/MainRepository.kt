package com.example.composablegithubapp.domain.repositories

import com.example.composablegithubapp.data.apis.MainApi
import com.example.composablegithubapp.domain.mappers.toGitHubResponse
import com.example.composablegithubapp.domain.model.GitHubResponse
import java.lang.Error
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: MainApi
) : IMainRepository {
    override suspend fun getResults(page: Int): Result<GitHubResponse> {
        api.getRepositories(page)
            .onSuccess {
                return Result.success(it.toGitHubResponse())
            }
            .onFailure {
                return Result.failure(it)
            }
        return Result.failure(Error())
    }
}
