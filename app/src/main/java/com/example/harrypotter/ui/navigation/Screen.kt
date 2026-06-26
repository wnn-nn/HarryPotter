package com.example.harrypotter.ui.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object SignUp : Screen("signup")
    data object Login : Screen("login")
    data object Dashboard : Screen("dashboard")
    data object Detail : Screen("detail/{id}")
    data object Favorites : Screen("favorites")
    data object Profile : Screen("profile")
    data object Edit : Screen("edit")
}