package com.example.composablegithubapp.ui.routes

import com.example.composablegithubapp.R

sealed class ScreenRoutes(val route: String, val resourceId: Int) {
    data object Main : ScreenRoutes(route = "MainScreen", resourceId = R.string.main_route)
}