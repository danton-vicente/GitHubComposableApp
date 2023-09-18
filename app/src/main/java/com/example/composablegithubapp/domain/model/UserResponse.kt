package com.example.composablegithubapp.domain.model

import java.io.Serializable

data class UserResponse(
    val userIcon: String,
    val userLogin: String,
) : Serializable
