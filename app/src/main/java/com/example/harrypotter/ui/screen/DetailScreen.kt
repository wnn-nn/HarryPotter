package com.example.harrypotter.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.harrypotter.ui.theme.*
import com.example.harrypotter.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(characterId: String, navController: NavController, detailViewModel: DetailViewModel) {
    val character by detailViewModel.characterDetail.collectAsState()
    val isFavorite by detailViewModel.isFavorite.collectAsState()

    LaunchedEffect(characterId) {
        detailViewModel.getCharacterDetail(characterId)
    }

//    TopAppBar(
//        title = { Text("Katalog Harry Potter") },
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = warnaAksen,
//            titleContentColor = warnaKertas
//        )
//    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Karakter",) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = warnaAksen,
                        titleContentColor = warnaKertas
                    ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali",tint = warnaKertas)
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        character?.let { data ->
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
                AsyncImage(
                    model = data.image.ifEmpty { "https://via.placeholder.com/300" },
                    contentDescription = data.name,
                    modifier = Modifier.fillMaxWidth().height(260.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(data.name, style = MaterialTheme.typography.headlineMedium)
                Text("Aktor: ${data.actor}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Asrama: ${data.house}", style = MaterialTheme.typography.titleMedium)
                Text("Keturunan: ${data.ancestry}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { detailViewModel.toggleFavorite(data) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = warnaAksen,
                        contentColor = warnaKertas
                    )
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Fav"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isFavorite) "Hapus dari Favorit" else "Simpan ke Favorit")
                }
            }
        }
    }
}