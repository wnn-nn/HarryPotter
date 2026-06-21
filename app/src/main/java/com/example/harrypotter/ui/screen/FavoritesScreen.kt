package com.example.harrypotter.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.example.harrypotter.viewmodel.MainViewModel
import com.example.harrypotter.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController, mainViewModel: MainViewModel) {
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
            if (favCharacters.isEmpty()) {
                Text("Belum ada favorit yang disimpan.", color = warnaTinta, modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    items(favCharacters) { fav ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
                                navController.navigate("detail/${fav.id}")
                            },
                            border = BorderStroke(1.dp, warnaTinta.copy(alpha = 0.3f)),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(model = fav.imageUrl, contentDescription = fav.name, modifier = Modifier.size(60.dp))
                                Column(modifier = Modifier.padding(start = 16.dp)) {
                                    Text(fav.name, style = MaterialTheme.typography.titleMedium, color = warnaTinta)
                                    Text("Asrama: ${fav.house}", style = MaterialTheme.typography.bodyMedium, color = warnaTinta)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}