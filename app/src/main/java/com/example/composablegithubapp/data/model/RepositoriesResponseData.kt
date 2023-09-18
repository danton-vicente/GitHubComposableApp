package com.example.composablegithubapp.data.model

import com.google.gson.annotations.SerializedName

data class RepositoriesResponseData (
    @SerializedName("full_name")
    val userName: String,
    @SerializedName("owner")
    val userData: UserResponseData,
    @SerializedName("forks_count")
    val forks: Int,
    @SerializedName("stargazers_count")
    val stars: Int,
)
