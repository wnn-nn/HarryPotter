package com.example.harrypotter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.harrypotter.viewmodel.*
import com.example.harrypotter.ui.navigation.Screen
import com.example.harrypotter.ui.screen.*


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Screen.Splash.route) {
                        composable(Screen.Splash.route) {
                            SplashScreen(navController, authViewModel)
                        }
                        composable(Screen.SignUp.route) {
                            SignUpScreen(navController, authViewModel)
                        }
                        composable(Screen.Login.route) {
                            LoginScreen(navController, authViewModel)
                        }
                        composable(Screen.Dashboard.route) {
                            DashboardScreen(navController, mainViewModel)
                        }

                        composable(
                            route = Screen.Detail.route,
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            DetailScreen(id, navController, detailViewModel)
                        }
                        composable(Screen.Favorites.route) {
                            FavoritesScreen(navController, mainViewModel)
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen(navController, authViewModel)
                        }
                    }
                }
            }
        }
    }
}