package com.example.composablegithubapp.domain.mappers

import com.example.composablegithubapp.data.model.GitHubResponseData
import com.example.composablegithubapp.data.model.RepositoriesResponseData
import com.example.composablegithubapp.data.model.UserResponseData
import com.example.composablegithubapp.domain.model.GitHubResponse
import com.example.composablegithubapp.domain.model.RepositoriesResponse
import com.example.composablegithubapp.domain.model.UserResponse

fun GitHubResponseData.toGitHubResponse() =
    GitHubResponse(
        incompleteResults = this.incompleteResults,
        totalCount = this.totalCount,
        repositories = this.repositories.toRepositoriesResponse()
    )

fun UserResponseData.toUserResponse() =
    UserResponse(
        userIcon = this.userIcon,
        userLogin = this.userLogin
    )

fun ArrayList<RepositoriesResponseData>.toRepositoriesResponse(): ArrayList<RepositoriesResponse> {
    val array = ArrayList<RepositoriesResponse>()
    this.map { data ->
        array.add(
            RepositoriesResponse(
                userData = data.userData.toUserResponse(),
                forks = data.forks,
                stars = data.stars,
                userName = data.userName
            )
        )
    }
    return array
}
