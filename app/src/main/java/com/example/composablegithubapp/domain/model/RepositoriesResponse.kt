package com.example.composablegithubapp.domain.model

data class RepositoriesResponse (
    val userName: String,
    val userData: UserResponse,
    val forks: Int,
    val stars: Int,
)
