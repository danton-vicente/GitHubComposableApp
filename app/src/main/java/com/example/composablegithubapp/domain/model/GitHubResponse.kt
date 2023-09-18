package com.example.composablegithubapp.domain.model

import java.io.Serializable

data class GitHubResponse(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val repositories: ArrayList<RepositoriesResponse>,
) : Serializable
