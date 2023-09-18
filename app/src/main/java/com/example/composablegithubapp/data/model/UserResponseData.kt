package com.example.composablegithubapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserResponseData(
    @SerializedName("avatar_url")
    val userIcon: String,
    @SerializedName("login")
    val userLogin: String
) : Serializable
