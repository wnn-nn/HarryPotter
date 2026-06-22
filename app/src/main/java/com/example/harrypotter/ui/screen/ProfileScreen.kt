package com.example.harrypotter.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.harrypotter.viewmodel.AuthViewModel
import com.example.harrypotter.ui.navigation.Screen
import com.example.harrypotter.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    val currentUser by authViewModel.currentUserProfile.collectAsState()

    val displayUsername = currentUser?.username ?: ""
    val displayEmail = currentUser?.email ?: ""
    val displayPhone = currentUser?.phone ?: ""
    val displayCity = currentUser?.city?.ifEmpty { "" } ?: ""
    val displayProvince = currentUser?.province?.ifEmpty { "" } ?: ""
    val displayCountry = currentUser?.country?.ifEmpty { "" } ?: ""
    val displayImageUrl = currentUser?.imageUrl

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil Akun") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = warnaAksen,
                    titleContentColor = warnaKertas,
                    actionIconContentColor = warnaKertas
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Edit.route) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Profil")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // --- FOTO PROFIL (Read-Only) ---
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(warnaTinta.copy(alpha = 0.1f))
                    .border(2.dp, warnaAksen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (!displayImageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = displayImageUrl,
                        contentDescription = "Foto Profil",
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(Icons.Default.Person, contentDescription = "Default", modifier = Modifier.size(80.dp), tint = warnaTinta.copy(alpha = 0.5f))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(value = displayUsername, onValueChange = {}, label = { Text("Username") }, readOnly = true, enabled = false, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = displayEmail, onValueChange = {}, label = { Text("Email") }, readOnly = true, enabled = false, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = displayPhone, onValueChange = {}, label = { Text("Nomor HP") }, readOnly = true, enabled = false, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = displayCity, onValueChange = {}, label = { Text("Kota") }, readOnly = true, enabled = false, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = displayProvince, onValueChange = {}, label = { Text("Provinsi") }, readOnly = true, enabled = false, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = displayCountry, onValueChange = {}, label = { Text("Negara") }, readOnly = true, enabled = false, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) { popUpTo(Screen.Dashboard.route) { inclusive = true } }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Keluar", color = Color.White, style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}