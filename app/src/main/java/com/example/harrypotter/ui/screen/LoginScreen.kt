package com.example.harrypotter.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.harrypotter.viewmodel.AuthViewModel
import com.example.harrypotter.ui.navigation.Screen
import com.example.harrypotter.ui.theme.*

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginSuccess by authViewModel.loginSuccess.collectAsState()

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selamat Datang",
            color = warnaTinta,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = warnaAksen,
                unfocusedBorderColor = warnaTinta,
                focusedTextColor = warnaTinta,
                unfocusedTextColor = warnaTinta,
                focusedLabelColor = warnaAksen
            ),
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = warnaAksen,
                unfocusedBorderColor = warnaTinta,
                focusedTextColor = warnaTinta,
                unfocusedTextColor = warnaTinta,
                focusedLabelColor = warnaAksen
            ),
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { authViewModel.login(username, password) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = warnaAksen, // Tombol warna cokelat kemerahan
                contentColor = warnaKertas   // Teks di dalam tombol warna kertas
            )
        ) {
            Text("Masuk",fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        TextButton(onClick = { navController.navigate(Screen.SignUp.route) }) {
            Text("Belum punya akun? Buat di sini",color = warnaTinta)
        }
    }
}