package com.example.harrypotter.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.harrypotter.R
import com.example.harrypotter.ui.navigation.Screen
import com.example.harrypotter.viewmodel.AuthViewModel
import com.example.harrypotter.ui.theme.*

@Composable
fun SplashScreen(navController: NavController, authViewModel: AuthViewModel) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    LaunchedEffect(Unit) {
        delay(2000)
        if (isLoggedIn) {
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(warnaTinta), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.logo_hp),
            contentDescription = "Logo",
            modifier = Modifier.size(360.dp)
        )
    }
}