package com.example.composablegithubapp.data.apis

import com.example.composablegithubapp.data.model.GitHubResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {

    @GET("search/repositories?q=language:Kotlin&sort=stars")
    suspend fun getRepositories(@Query("page") page: Int): Result<GitHubResponseData>
}