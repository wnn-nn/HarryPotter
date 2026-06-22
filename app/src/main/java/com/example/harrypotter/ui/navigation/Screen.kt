package com.example.harrypotter.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object SignUp : Screen("signup")
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
//    object SearchSpells : Screen("search_spells")
    object Detail : Screen("detail/{id}")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
    object Edit : Screen("edit")
}