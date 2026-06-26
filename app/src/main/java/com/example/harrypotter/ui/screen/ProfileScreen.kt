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
import com.example.harrypotter.ui.component.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    // Mengambil profil pengguna yang sedang login saat ini
    val currentUser by authViewModel.currentUserProfile.collectAsState()

    // Mempersiapkan variabel teks untuk ditampilkan. Jika null, ubah menjadi string kosong ("")
    val displayUsername = currentUser?.username ?: ""
    val displayEmail = currentUser?.email ?: ""
    val displayPhone = currentUser?.phone ?: ""
    val displayCity = currentUser?.city?.ifEmpty { "" } ?: ""
    val displayProvince = currentUser?.province?.ifEmpty { "" } ?: ""
    val displayCountry = currentUser?.country?.ifEmpty { "" } ?: ""
    val displayImageUrl = currentUser?.imageUrl

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Profil Akun",
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Edit.route) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Profil")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        // Column dibungkus dengan verticalScroll agar halaman bisa di-scroll jika layar HP kecil
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Box untuk menampilkan bingkai Foto Profil
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(warnaTinta.copy(alpha = 0.1f))
                    .border(2.dp, warnaAksen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Jika url foto tidak kosong, tampilkan gambar
                if (!displayImageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = displayImageUrl,
                        contentDescription = "Foto Profil",
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop // Memotong paksa gambar jadi bulat
                    )
                } else {
                    // Jika belum ada foto, tampilkan ikon orang standar (default)
                    Icon(Icons.Default.Person, contentDescription = "Default", modifier = Modifier.size(80.dp), tint = warnaTinta.copy(alpha = 0.5f))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Menggunakan CustomTextField dengan isReadOnly = true agar tidak bisa diedit di layar ini
            CustomTextField(value = displayUsername, onValueChange = {}, label = "Username", isReadOnly = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = displayEmail, onValueChange = {}, label = "Email", isReadOnly = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = displayPhone, onValueChange = {}, label = "Nomor HP", isReadOnly = true)

            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = displayCity, onValueChange = {}, label = "Kota", isReadOnly = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = displayProvince, onValueChange = {}, label = "Provinsi", isReadOnly = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = displayCountry, onValueChange = {}, label = "Negara", isReadOnly = true)

            Spacer(modifier = Modifier.height(32.dp))

            // Tombol Logout
            Button(
                onClick = {
                    // Membersihkan sesi login (misalnya DataStore)
                    authViewModel.logout()
                    // Pindah ke layar Login dan hapus seluruh tumpukan layar sebelumnya
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