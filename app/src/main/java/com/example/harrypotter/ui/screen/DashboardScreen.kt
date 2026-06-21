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
import com.example.harrypotter.viewmodel.MainViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import com.example.harrypotter.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, mainViewModel: MainViewModel) {
    var searchQuery by remember { mutableStateOf("")}
    val characters by mainViewModel.characters.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Katalog Harry Potter") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = warnaAksen,
                    titleContentColor = warnaKertas
                )
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = warnaAksen)
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
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
                    LazyColumn {
                        items(characters.filter { it.name.contains(searchQuery, ignoreCase = true) }) { character ->
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
                                    navController.navigate("detail/${character.id}")
                                },
                                border = BorderStroke(1.dp, warnaTinta.copy(alpha = 0.3f)),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = character.image.ifEmpty { "https://via.placeholder.com/150" },
                                        contentDescription = character.name,
                                        modifier = Modifier.size(70.dp)
                                    )
                                    Column(modifier = Modifier.padding(start = 16.dp)) {
                                        Text(
                                            character.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = warnaTinta
                                        )
                                        Text(
                                            "Aktor: ${character.actor}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = warnaTinta
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}