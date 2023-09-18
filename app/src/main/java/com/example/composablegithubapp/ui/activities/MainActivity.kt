package com.example.composablegithubapp.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.composablegithubapp.ui.navigation.MainNavGraph
import com.example.composablegithubapp.ui.routes.ScreenRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                MainNavGraph(startDestination = ScreenRoutes.Main.route,
                    navController = navController,
                    onBackpressed = { onBackButtonPressed() },
                    errorToast = {
                        Toast.makeText(applicationContext, it, Toast.LENGTH_LONG)
                    })
            }
        }
    }

    private fun onBackButtonPressed(){
        this.onBackPressed()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}