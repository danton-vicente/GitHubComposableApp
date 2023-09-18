package com.example.composablegithubapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composablegithubapp.ui.composables.screens.MainScreen
import com.example.composablegithubapp.ui.routes.ScreenRoutes

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String,
    onBackpressed: () -> Unit,
    errorToast: (String) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(ScreenRoutes.Main.route) {
            MainScreen(navController = navController,
                onBackPressed = onBackpressed,
                errorToast = errorToast)
        }
    }
}