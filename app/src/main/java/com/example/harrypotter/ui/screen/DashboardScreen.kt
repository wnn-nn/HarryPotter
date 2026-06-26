package com.example.harrypotter.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.harrypotter.viewmodel.MainViewModel
import com.example.harrypotter.ui.theme.*
import com.example.harrypotter.ui.component.*

@Composable
fun DashboardScreen(navController: NavController, mainViewModel: MainViewModel) {
    // Variabel untuk menyimpan teks pencarian
    var searchQuery by remember { mutableStateOf("")}

    // Mengambil daftar karakter dan status loading dari ViewModel (API)
    val characters by mainViewModel.characters.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()

    Scaffold(
        topBar = { CustomTopAppBar(title = "Katalog Harry Potter") },
        bottomBar = { BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            // Jika data masih diambil dari internet, tampilkan animasi loading (lingkaran berputar)
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = warnaAksen)
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Kotak Input untuk Pencarian
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it }, // Update state setiap kali diketik
                        label = { Text("Cari nama karakter...") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = warnaAksen,
                            unfocusedBorderColor = warnaTinta,
                            focusedTextColor = warnaTinta,
                            unfocusedTextColor = warnaTinta,
                            focusedLabelColor = warnaAksen
                        ),
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )

                    // LazyColumn: Daftar gulir (scrollable list) yang hemat memori
                    LazyColumn {
                        // Memfilter daftar berdasarkan teks pencarian
                        items(characters.filter { it.name.contains(searchQuery, ignoreCase = true) }) { character ->
                            // Menampilkan komponen Kartu
                            CharacterCard(
                                imageUrl = character.image,
                                title = character.name,
                                subtitle = "Aktor: ${character.actor}",
                                onClick = { navController.navigate("detail/${character.id}") } // Klik kartu menuju halaman detail
                            )
                        }
                    }
                }
            }
        }
    }
}