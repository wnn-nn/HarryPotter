package com.example.harrypotter.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
// import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.harrypotter.ui.theme.*


@Composable
fun BottomNavigationBar(navController: NavController) {

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        val navColors = NavigationBarItemDefaults.colors(
            selectedIconColor = warnaAksen,
            selectedTextColor = warnaAksen,
            unselectedIconColor = warnaTinta.copy(alpha = 0.5f),
            unselectedTextColor = warnaTinta.copy(alpha = 0.5f)
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Katalog") },
            selected = false,
            colors = navColors,
            onClick = { navController.navigate("dashboard") }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Fav") },
            label = { Text("Favorit") },
            selected = false,
            colors = navColors,
            onClick = { navController.navigate("favorites") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profil") },
            selected = false,
            colors = navColors,
            onClick = { navController.navigate("profile") }
        )
    }
}