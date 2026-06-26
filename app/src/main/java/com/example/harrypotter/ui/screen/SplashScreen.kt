package com.example.harrypotter.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.navigation.NavController
import kotlinx.coroutines.delay

import com.example.harrypotter.R
import com.example.harrypotter.ui.navigation.Screen
import com.example.harrypotter.viewmodel.AuthViewModel
import com.example.harrypotter.ui.theme.*

@Composable
fun SplashScreen(navController: NavController, authViewModel: AuthViewModel) {
    // Memantau status login pengguna secara real-time dari ViewModel
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    // LaunchedEffect berjalan satu kali saat layar ini pertama kali dibuka
    LaunchedEffect(Unit) {
        // Menahan layar splash selama 2 detik (2000 milidetik)
        delay(2000)

        // Pengecekan status login
        if (isLoggedIn) {
            // Jika sudah login, langsung diarahkan ke Dashboard
            navController.navigate(Screen.Dashboard.route) {
                // Hapus layar Splash dari riwayat agar tidak bisa di-back
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        } else {
            // Jika belum login, diarahkan ke layar Login
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    // UI Layar Splash: Kotak penuh berisi logo di tengah
    Box(modifier = Modifier.fillMaxSize().background(warnaTinta), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.logo_hp),
            contentDescription = "Logo",
            modifier = Modifier.size(360.dp)
        )
    }
}