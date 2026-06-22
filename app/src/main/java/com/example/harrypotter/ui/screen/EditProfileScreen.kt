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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    val currentUser by authViewModel.currentUserProfile.collectAsState()

    val displayUsername = currentUser?.username ?: ""
    val displayEmail = currentUser?.email ?: ""
    val displayPhone = currentUser?.phone ?: ""

    // Mengambil data lama (jika ada) sebagai nilai awal
    var displayCity by remember(currentUser) { mutableStateOf(currentUser?.city ?: "") }
    var displayProvince by remember(currentUser) { mutableStateOf(currentUser?.province ?: "") }
    var displayCountry by remember(currentUser) { mutableStateOf(currentUser?.country ?: "") }

    // Konversi String dari DB menjadi Uri untuk ditampilkan
    var imageUri by remember(currentUser) {
        mutableStateOf(if (currentUser?.imageUrl.isNullOrEmpty()) null else Uri.parse(currentUser?.imageUrl))
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) imageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profil") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = warnaAksen,
                    titleContentColor = warnaKertas,
                    navigationIconContentColor = warnaKertas
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
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

            // --- FOTO PROFIL ---
            Box(
                modifier = Modifier.size(130.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(warnaTinta.copy(alpha = 0.1f))
                        .border(2.dp, warnaAksen, CircleShape)
                        .clickable { launcher.launch("image/*") },
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
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = (-4).dp, y = (-4).dp)
                        .background(warnaAksen, CircleShape)
                        .border(2.dp, warnaKertas, CircleShape)
                        .clickable { launcher.launch("image/*") }
                        .padding(8.dp)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Ganti", tint = warnaKertas, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- DATA AKUN (Read Only) ---
            OutlinedTextField(value = displayUsername, onValueChange = {}, label = { Text("Username") }, readOnly = true, enabled = false, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = displayEmail, onValueChange = {}, label = { Text("Email") }, readOnly = true, enabled = false, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = displayPhone, onValueChange = {}, label = { Text("Nomor HP") }, readOnly = true, enabled = false, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(24.dp))

            // --- DATA TAMBAHAN (Bisa Edit) ---
            OutlinedTextField(value = displayCity, onValueChange = { displayCity = it }, label = { Text("Kota") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = displayProvince, onValueChange = { displayProvince = it }, label = { Text("Provinsi") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(value = displayCountry, onValueChange = { displayCountry = it }, label = { Text("Negara") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(32.dp))

            // --- TOMBOL SIMPAN ---
            Button(
                onClick = {
                    currentUser?.let { user ->
                        val updatedUser = user.copy(
                            city = displayCity,
                            province = displayProvince,
                            country = displayCountry,
                            imageUrl = imageUri?.toString() ?: "" // Simpan URL gambar ke database
                        )
                        authViewModel.updateUserProfile(updatedUser)
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