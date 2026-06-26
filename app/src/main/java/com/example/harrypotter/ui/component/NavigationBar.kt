package com.example.harrypotter.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

import com.example.harrypotter.ui.theme.warnaAksen
import com.example.harrypotter.ui.theme.warnaKertas
import com.example.harrypotter.ui.theme.warnaTinta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    showBackButton: Boolean = false, // Default-nya tidak ada tombol back
    onBackClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {} // Untuk tambahan tombol di kanan (seperti Edit)
) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = warnaAksen,
            titleContentColor = warnaKertas,
            navigationIconContentColor = warnaKertas,
            actionIconContentColor = warnaKertas
        ),
        navigationIcon = {
            // Hanya tampilkan tombol panah mundur jika showBackButton = true
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                }
            }
        },
        actions = actions
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        val navColors = NavigationBarItemDefaults.colors(
            selectedIconColor = warnaTinta,
            selectedTextColor = warnaTinta,
            unselectedIconColor = warnaAksen,
            unselectedTextColor = warnaAksen
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