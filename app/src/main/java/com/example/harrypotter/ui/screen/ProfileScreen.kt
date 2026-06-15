package com.example.harrypotter.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.harrypotter.viewmodel.AuthViewModel
import com.example.harrypotter.ui.navigation.Screen
import com.example.harrypotter.ui.screen.BottomNavigationBar
import com.example.harrypotter.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    val currentUser by authViewModel.currentUserProfile.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil Akun") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = warnaAksen,
                    titleContentColor = warnaKertas
                )
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Avatar", modifier = Modifier.size(120.dp), tint = warnaAksen)
            Spacer(modifier = Modifier.height(16.dp))

            val displayUsername = currentUser?.username ?: "Wanda Fitriardi"
            val displayEmail = currentUser?.email ?: "wanda@student.unand.ac.id"
            val displayPhone = currentUser?.phone ?: "08123456789"

            Text("Nama / User: $displayUsername", style = MaterialTheme.typography.titleLarge, color = warnaTinta)
            Text("NIM: 2411521004", style = MaterialTheme.typography.bodyLarge, color = warnaTinta)
            Text("Prodi: Sistem Informasi", style = MaterialTheme.typography.bodyLarge, color = warnaTinta)
            Text("Kontak: $displayPhone | $displayEmail", style = MaterialTheme.typography.bodySmall, color = warnaTinta)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Keluar (Logout)", color = Color.White)
            }
        }
    }
}