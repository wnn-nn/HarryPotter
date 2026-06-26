package com.example.harrypotter.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

import com.example.harrypotter.viewmodel.MainViewModel
import com.example.harrypotter.ui.theme.*
import com.example.harrypotter.ui.component.BottomNavigationBar
import com.example.harrypotter.ui.component.CharacterCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController, mainViewModel: MainViewModel) {
    // Mengambil list karakter yang disukai dari Database Lokal
    val favCharacters by mainViewModel.favoriteCharacters.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Karakter Favorit Anda") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = warnaAksen,
                    titleContentColor = warnaKertas
                )
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Pengecekan: Jika daftarnya kosong, tampilkan pesan peringatan
            if (favCharacters.isEmpty()) {
                Text("Belum ada favorit yang disimpan.", color = warnaTinta, modifier = Modifier.align(Alignment.Center))
            } else {
                // Jika ada isinya, tampilkan dalam bentuk list gulir
                LazyColumn {
                    items(favCharacters) { fav ->
                        CharacterCard(
                            imageUrl = fav.imageUrl,
                            title = fav.name,
                            subtitle = "Asrama: ${fav.house}",
                            onClick = { navController.navigate("detail/${fav.id}") }
                        )
                    }
                }
            }
        }
    }
}