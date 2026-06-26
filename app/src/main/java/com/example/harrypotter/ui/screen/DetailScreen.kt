package com.example.harrypotter.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

import com.example.harrypotter.ui.theme.*
import com.example.harrypotter.viewmodel.DetailViewModel
import com.example.harrypotter.ui.component.*

@Composable
fun DetailScreen(characterId: String, navController: NavController, detailViewModel: DetailViewModel) {
    // Mengambil data detail karakter dan status favorit dari ViewModel secara real-time
    val character by detailViewModel.characterDetail.collectAsState()
    val isFavorite by detailViewModel.isFavorite.collectAsState()

    // Menjalankan pengambilan data dari API saat layar ini pertama kali dibuka
    LaunchedEffect(characterId) {
        detailViewModel.getCharacterDetail(characterId)
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Detail Karakter",
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
            )
        },
    ) { paddingValues ->
        // Pengecekan: Hanya render (tampilkan) UI jika data karakter tidak kosong (null)
        character?.let { data ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp)
            ) {
                // Menampilkan gambar karakter dengan sudut membulat
                AsyncImage(
                    model = data.image.ifEmpty { "https://via.placeholder.com/300" },
                    contentDescription = data.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Menampilkan Nama karakter dan Aktor pemerannya
                Text(data.name, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = warnaTinta)
                Text("Diperankan oleh: ${data.actor}", style = MaterialTheme.typography.titleMedium, color = warnaTinta.copy(alpha = 0.7f))

                Spacer(modifier = Modifier.height(24.dp))

                // Kotak Kartu untuk membungkus spesifikasi detail
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, warnaTinta.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Menggunakan komponen baris buatan sendiri
                        DetailRow("Asrama", data.house.ifEmpty { "-" })
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = warnaTinta.copy(alpha = 0.1f))

                        DetailRow("Gender", data.gender.ifEmpty { "-" })
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = warnaTinta.copy(alpha = 0.1f))

                        DetailRow("Keturunan", data.ancestry.ifEmpty { "-" })
                    }
                }

                // Trik 'weight' untuk mengambil seluruh sisa ruang kosong di tengah layar
                // agar tombol favorit di bawahnya terdorong mentok ke dasar layar
//                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(24.dp))

                // Tombol Dinamis: Mengubah warna dan ikon berdasarkan status favorit
                OutlinedButton(
                    onClick = { detailViewModel.toggleFavorite(data) }, // Aksi menukar status favorit
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    border = BorderStroke(
                        width = 1.5.dp,
                        color = if (isFavorite) MaterialTheme.colorScheme.error else warnaTinta.copy(alpha = 0.5f)
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = if (isFavorite) MaterialTheme.colorScheme.error else warnaTinta
                    )
                ) {
                    Icon(imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder, contentDescription = "Fav")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isFavorite) "Hapus dari Favorit" else "Simpan ke Favorit", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}