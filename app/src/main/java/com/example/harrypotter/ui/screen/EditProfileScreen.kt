package com.example.harrypotter.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

import com.example.harrypotter.ui.theme.*
import com.example.harrypotter.viewmodel.AuthViewModel
import com.example.harrypotter.ui.component.*

@Composable
fun EditProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    // Ambil data user yang login saat ini
    val currentUser by authViewModel.currentUserProfile.collectAsState()

    // Data sensitif yang tidak boleh diedit
    val displayUsername = currentUser?.username ?: ""
    val displayEmail = currentUser?.email ?: ""
    val displayPhone = currentUser?.phone ?: ""

    // State untuk inputan alamat, nilai awalnya diisi dengan data lama dari database
    var displayCity by remember(currentUser) { mutableStateOf(currentUser?.city ?: "") }
    var displayProvince by remember(currentUser) { mutableStateOf(currentUser?.province ?: "") }
    var displayCountry by remember(currentUser) { mutableStateOf(currentUser?.country ?: "") }

    // State untuk foto, nilainya diparsing (diubah) dari bentuk String (di DB) menjadi Uri
    var imageUri by remember(currentUser) {
        mutableStateOf(if (currentUser?.imageUrl.isNullOrEmpty()) null else Uri.parse(currentUser?.imageUrl))
    }

    // Launcher untuk membuka Galeri bawaan HP dan memilih foto
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Jika user berhasil memilih foto, simpan alamat urinya
        if (uri != null) imageUri = uri
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Detail Karakter",
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
            )
        }
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

            // Area Foto Profil yang bisa di-klik untuk membuka Galeri
            Box(
                modifier = Modifier.size(130.dp),
                contentAlignment = Alignment.Center
            ) {
                // Lingkaran foto utama
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(warnaTinta.copy(alpha = 0.1f))
                        .border(2.dp, warnaAksen, CircleShape)
                        .clickable { launcher.launch("image/*") }, // Memanggil launcher galeri saat di-klik
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Foto Profil",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(Icons.Default.Person, contentDescription = "Default", modifier = Modifier.size(80.dp), tint = warnaTinta.copy(alpha = 0.5f))
                    }
                }

                // Ikon kamera kecil di pojok kanan bawah
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = (-4).dp, y = (-4).dp)
                        .background(warnaAksen, CircleShape)
                        .border(2.dp, warnaKertas, CircleShape)
                        .clickable { launcher.launch("image/*") } // Juga membuka galeri
                        .padding(8.dp)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Ganti", tint = warnaKertas, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Kolom Read-Only (Kunci)
            CustomTextField(value = displayUsername, onValueChange = {}, label = "Username", isReadOnly = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = displayEmail, onValueChange = {}, label = "Email", isReadOnly = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = displayPhone, onValueChange = {}, label = "Nomor HP", isReadOnly = true)

            Spacer(modifier = Modifier.height(24.dp))

            // Kolom Editable (Bisa diisi/ubah)
            CustomTextField(value = displayCity, onValueChange = { displayCity = it }, label = "Kota")
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = displayProvince, onValueChange = { displayProvince = it }, label = "Provinsi")
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(value = displayCountry, onValueChange = { displayCountry = it }, label = "Negara")

            Spacer(modifier = Modifier.height(32.dp))

            // Tombol Simpan Perubahan
            Button(
                onClick = {
                    // Update data ke database Lokal
                    currentUser?.let { user ->
                        val updatedUser = user.copy(
                            city = displayCity,
                            province = displayProvince,
                            country = displayCountry,
                            imageUrl = imageUri?.toString() ?: "" // Mengubah Uri kembali ke format String
                        )
                        authViewModel.updateUserProfile(updatedUser)
                        // Kembali ke layar Profil setelah sukses menyimpan
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = warnaAksen, contentColor = warnaKertas)
            ) {
                Text("Simpan Profil", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}