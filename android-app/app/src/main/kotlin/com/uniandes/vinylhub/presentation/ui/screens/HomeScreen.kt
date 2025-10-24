package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uniandes.vinylhub.ui.theme.VinylHubTheme

@Composable
fun HomeScreen(
    onNavigateToAlbums: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🎵 VinylHub",
            fontSize = 48.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Bienvenido a VinylHub",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Explora nuestro catálogo de álbumes, artistas y colecciones",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 48.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = onNavigateToAlbums,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Ver Catálogo de Álbumes")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    VinylHubTheme {
        HomeScreen(onNavigateToAlbums = {})
    }
}

