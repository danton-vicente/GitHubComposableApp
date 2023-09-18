package com.example.composablegithubapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GitHubResponseData(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val repositories: ArrayList<RepositoriesResponseData>,
) : Serializable
