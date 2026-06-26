package com.example.harrypotter.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.harrypotter.viewmodel.AuthViewModel
import com.example.harrypotter.ui.navigation.Screen
import com.example.harrypotter.ui.theme.*
import com.example.harrypotter.ui.component.CustomTextField

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    // 1. Variabel state untuk menyimpan inputan dan pesan
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var infoMessage by remember { mutableStateOf("") } // <-- INI YANG TERLEWAT!

    // Memantau apakah proses login berhasil dari ViewModel
    val loginSuccess by authViewModel.loginSuccess.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()

    // Jika loginSuccess berubah menjadi true, otomatis pindah ke Dashboard
    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    // Menangkap pesan error dari ViewModel
    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            infoMessage = errorMessage
        }
    }

    // Semua tampilan visual dimasukkan ke dalam Column agar rapi ke bawah
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selamat Datang",
            color = warnaTinta,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Input untuk Username
        CustomTextField(
            value = username,
            onValueChange = { username = it },
            label = "Username"
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Input untuk Password
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(24.dp))

        // 2. Menampilkan teks error merah (sekarang posisinya aman di dalam Column)
        if (infoMessage.isNotEmpty()) {
            Text(
                text = infoMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Tombol Masuk
        Button(
            onClick = { authViewModel.login(username, password) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = warnaAksen,
                contentColor = warnaKertas
            )
        ) {
            Text("Masuk", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        // Teks pendaftaran
        TextButton(onClick = { navController.navigate(Screen.SignUp.route) }) {
            Text("Belum punya akun? Buat di sini", color = warnaTinta)
        }
    }
}
