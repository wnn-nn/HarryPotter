package com.example.harrypotter.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

import com.example.harrypotter.viewmodel.AuthViewModel
import com.example.harrypotter.ui.navigation.Screen
import com.example.harrypotter.data.local.UserEntity
import com.example.harrypotter.ui.theme.*
import com.example.harrypotter.ui.component.CustomTextField

@Composable
fun SignUpScreen(navController: NavController, authViewModel: AuthViewModel) {
    // Variabel untuk menyimpan data pendaftaran
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var infoMessage by remember { mutableStateOf("") } // Untuk menampilkan pesan error

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Buat Akun Baru",
            color = warnaTinta,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Rentetan input pendaftaran menggunakan komponen Custom
        CustomTextField(value = username, onValueChange = { username = it }, label = "Username")
        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(value = email, onValueChange = { email = it }, label = "Email")
        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(value = phone, onValueChange = { phone = it }, label = "No HP")
        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(value = password, onValueChange = { password = it }, label = "Password", isPassword = true)
        Spacer(modifier = Modifier.height(24.dp)) // Jarak disesuaikan agar rapi

        // 1. Teks Error dipindah ke sini (tepat di atas tombol, persis seperti LoginScreen)
        if (infoMessage.isNotEmpty()) {
            Text(
                text = infoMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Tombol Daftar
        Button(
            onClick = {
                // 2. Validasi: isNotBlank memastikan spasi kosong tidak dihitung sebagai ketikan
                if (username.isNotBlank() && email.isNotBlank() && phone.isNotBlank() && password.isNotBlank()) {

                    infoMessage = "" // Kosongkan error jika berhasil

                    // Menyimpan data ke Database Lokal (Room)
                    authViewModel.registerUser(UserEntity(username, email, phone, password))

                    // Kembali ke halaman Login setelah sukses mendaftar
                    navController.navigate(Screen.Login.route) {
                        // Bersihkan tumpukan halaman agar tidak menumpuk saat di-back
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                } else {
                    infoMessage = "Semua kolom tidak boleh ada yang kosong!"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = warnaAksen, contentColor = warnaKertas)
        ) {
            Text("Daftar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Teks untuk kembali ke Login jika sudah punya akun
        TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
            Text("Sudah punya akun? Masuk di sini", color = warnaTinta)
        }
    }
}