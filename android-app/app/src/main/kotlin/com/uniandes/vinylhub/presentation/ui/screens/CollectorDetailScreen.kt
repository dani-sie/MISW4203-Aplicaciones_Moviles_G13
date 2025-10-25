package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.util.Log
import coil.compose.AsyncImage
import com.uniandes.vinylhub.data.model.Collector
import com.uniandes.vinylhub.data.model.CollectorComment
import com.uniandes.vinylhub.data.model.FavoritePerformer
import com.uniandes.vinylhub.data.model.CollectorAlbum
import com.uniandes.vinylhub.presentation.viewmodel.CollectorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectorDetailScreen(
    collectorId: Int,
    viewModel: CollectorViewModel?,
    onBackClick: () -> Unit
) {
    val collector = remember { mutableStateOf<Collector?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(collectorId) {
        if (viewModel != null) {
            try {
                Log.d("CollectorDetailScreen", "Cargando coleccionista $collectorId...")
                isLoading.value = true
                val fetchedCollector = viewModel.getCollectorById(collectorId)
                Log.d("CollectorDetailScreen", "Coleccionista obtenido: ${fetchedCollector?.name}")
                collector.value = fetchedCollector
                if (fetchedCollector == null) {
                    error.value = "Coleccionista no encontrado"
                    Log.e("CollectorDetailScreen", "Coleccionista $collectorId no encontrado")
                }
            } catch (e: Exception) {
                error.value = "Error al cargar el coleccionista: ${e.message}"
                Log.e("CollectorDetailScreen", "Error al cargar coleccionista $collectorId", e)
            } finally {
                isLoading.value = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            isLoading.value -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            error.value != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = error.value ?: "Error desconocido")
                }
            }
            collector.value != null -> {
                CollectorDetailContent(
                    collector = collector.value!!,
                    onBackClick = onBackClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun CollectorDetailContent(
    collector: Collector,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
    ) {
        item {
            // Collector header with image and info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Collector image - circular
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.LightGray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (!collector.image.isNullOrEmpty()) {
                        AsyncImage(
                            model = collector.image,
                            contentDescription = "Imagen de ${collector.name}",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color(0xFFDDDDDD), CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text("👤", fontSize = 40.sp)
                    }
                }

                // Collector name
                Text(
                    text = collector.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 12.dp)
                )

                // Contact info
                if (collector.telephone.isNotEmpty()) {
                    Text(
                        text = "Tel: ${collector.telephone}",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                if (collector.email.isNotEmpty()) {
                    Text(
                        text = collector.email,
                        fontSize = 13.sp,
                        color = Color(0xFF2196F3),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                // Badges
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CollectorDetailBadgeChip(
                        label = "${collector.commentsCount} comentario${if (collector.commentsCount != 1) "s" else ""}",
                        backgroundColor = Color(0xFF2196F3)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    CollectorDetailBadgeChip(
                        label = "${collector.favoritesCount} favorito${if (collector.favoritesCount != 1) "s" else ""}",
                        backgroundColor = Color(0xFF00BCD4)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    CollectorDetailBadgeChip(
                        label = "${collector.albumsCount} álbum${if (collector.albumsCount != 1) "es" else ""}",
                        backgroundColor = Color(0xFF9E9E9E)
                    )
                }
            }
        }

        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        }

        // Comments section
        if (collector.comments.isNotEmpty()) {
            item {
                Text(
                    text = "COMENTARIOS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(collector.comments) { comment ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFE0E0E0))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = comment.description,
                            fontSize = 13.sp,
                            color = Color(0xFF333333),
                            modifier = Modifier.weight(1f)
                        )
                        // Rating badge
                        Box(
                            modifier = Modifier
                                .background(Color(0xFF4CAF50), RoundedCornerShape(12.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "★${comment.rating}",
                                fontSize = 12.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            }
        }

        // Favorite performers section
        if (collector.favoritePerformers.isNotEmpty()) {
            item {
                Text(
                    text = "ARTISTAS FAVORITOS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(collector.favoritePerformers) { performer ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFE0E0E0))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Performer image
                        if (!performer.image.isNullOrEmpty()) {
                            AsyncImage(
                                model = performer.image,
                                contentDescription = performer.name ?: "Performer",
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .border(1.5.dp, Color(0xFFBBBBBB), CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(Color(0xFFCCCCCC), CircleShape)
                                    .border(1.5.dp, Color(0xFFBBBBBB), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("👤", fontSize = 24.sp)
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = performer.name ?: "Sin nombre",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333)
                            )
                            val performerDate = performer.getDate()
                            if (performerDate.isNotEmpty() && performerDate.length >= 10) {
                                val label = if (!performer.birthDate.isNullOrEmpty()) "Nac." else "Creación"
                                Text(
                                    text = "$label: ${performerDate.substring(0, 10)}",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                            if (!performer.description.isNullOrEmpty()) {
                                Text(
                                    text = performer.description,
                                    fontSize = 11.sp,
                                    color = Color(0xFF666666),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            }
        }

        // Collector albums section
        if (collector.collectorAlbums.isNotEmpty()) {
            item {
                Text(
                    text = "ÁLBUMES EN COLECCIÓN",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(collector.collectorAlbums) { album ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFE0E0E0))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "ID ${album.id}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333)
                            )
                            Text(
                                text = "Precio: $${album.price}",
                                fontSize = 12.sp,
                                color = Color(0xFF666666),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Status badge
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (album.status == "Active") Color(0xFF4CAF50) else Color(0xFF9E9E9E),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = album.status,
                                    fontSize = 11.sp,
                                    color = Color.White
                                )
                            }
                            // Ver álbum link
                            TextButton(
                                onClick = { /* TODO: Navigate to album */ }
                            ) {
                                Text(
                                    text = "Ver álbum",
                                    fontSize = 12.sp,
                                    color = Color(0xFF2196F3)
                                )
                            }
                        }
                    }
                }
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            }
        }

        item {
            // Action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = { /* TODO: Implement call */ }
                ) {
                    Text("Llamar", color = Color(0xFF2196F3))
                }
                OutlinedButton(
                    onClick = { /* TODO: Implement send email */ },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF2196F3)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF2196F3))
                ) {
                    Text("Enviar correo")
                }
            }
        }

        item {
            // Back button
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF2196F3)
                ),
                border = BorderStroke(1.dp, Color(0xFF2196F3))
            ) {
                Text("Volver a los coleccionistas")
            }
        }
    }
}

@Composable
private fun CollectorDetailBadgeChip(
    label: String,
    backgroundColor: Color
) {
    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, widthDp = 390, heightDp = 1000)
@Composable
fun CollectorDetailScreenPreview() {
    val sampleCollector = Collector(
        id = 100,
        name = "Manolo Bellon",
        telephone = "3502457896",
        email = "manollo@caracol.com.co",
        image = "https://i.pravatar.cc/150?u=100",
        commentsCount = 1,
        favoritesCount = 1,
        albumsCount = 1
    ).apply {
        comments = listOf(
            CollectorComment(
                id = 100,
                description = "The most relevant album of Ruben Blades",
                rating = 5
            )
        )
        favoritePerformers = listOf(
            FavoritePerformer(
                id = 100,
                name = "Rubén Blades Bellido de Luna",
                image = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Ruben_Blades_by_Gage_Skidmore.jpg/800px-Ruben_Blades_by_Gage_Skidmore.jpg",
                description = "Es un cantante, compositor, músico, actor, abogado, político y activista panameño. Ha desarrollado gran parte de su carrera artística en la ciudad de Nueva York.",
                birthDate = "1948-07-16T00:00:00.000Z"
            )
        )
        collectorAlbums = listOf(
            CollectorAlbum(
                id = 100,
                price = 35,
                status = "Active"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        CollectorDetailContent(collector = sampleCollector, onBackClick = {}, modifier = Modifier.padding(paddingValues))
    }
}

