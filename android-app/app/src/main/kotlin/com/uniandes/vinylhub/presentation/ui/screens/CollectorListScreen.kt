package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.uniandes.vinylhub.R
import com.uniandes.vinylhub.data.model.Collector
import com.uniandes.vinylhub.presentation.viewmodel.CollectorViewModel

@Composable
fun CollectorListScreen(
    viewModel: CollectorViewModel?,
    onCollectorClick: (Int) -> Unit,
    onBackToHome: () -> Unit = {}
) {
    val context = LocalContext.current
    val collectors: List<Collector> by viewModel?.collectors?.collectAsState(initial = emptyList()) ?: androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = context.getString(R.string.title_collectors),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Search bar
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Buscar usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(24.dp),
            singleLine = true
        )

        if (collectors.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = context.getString(R.string.loading_collectors),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(collectors) { collector ->
                    CollectorListItem(
                        collector = collector,
                        onClick = { onCollectorClick(collector.id) }
                    )
                }
            }
        }

        // Back to home button
        androidx.compose.material3.OutlinedButton(
            onClick = onBackToHome,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, androidx.compose.ui.graphics.Color(0xFF1976D2))
        ) {
            androidx.compose.material3.Text("Volver al menú principal", color = androidx.compose.ui.graphics.Color(0xFF1976D2))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CollectorListItem(
    collector: Collector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .border(
                width = 1.dp,
                color = androidx.compose.ui.graphics.Color(0xFFDDDDDD),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
        // Collector image - circular with rounded corners
        Box(
            modifier = Modifier
                .size(64.dp)
                .border(
                    width = 1.dp,
                    color = androidx.compose.ui.graphics.Color(0xFFDDDDDD),
                    shape = CircleShape
                )
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (!collector.image.isNullOrEmpty()) {
                AsyncImage(
                    model = collector.image,
                    contentDescription = collector.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("👤", fontSize = 32.sp)
            }
        }

        // Collector info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = collector.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Tel: ${collector.telephone}",
                fontSize = 12.sp,
                color = androidx.compose.ui.graphics.Color(0xFF666666),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = collector.email,
                fontSize = 12.sp,
                color = androidx.compose.ui.graphics.Color(0xFF666666),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            // Badges row with wrap
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                CollectorBadgeChip(
                    label = "${collector.commentsCount} comentario${if (collector.commentsCount != 1) "s" else ""}",
                    backgroundColor = androidx.compose.ui.graphics.Color(0xFF2196F3)
                )
                CollectorBadgeChip(
                    label = "${collector.favoritesCount} favorito${if (collector.favoritesCount != 1) "s" else ""}",
                    backgroundColor = androidx.compose.ui.graphics.Color(0xFF00BCD4)
                )
                CollectorBadgeChip(
                    label = "${collector.albumsCount} álbum${if (collector.albumsCount != 1) "es" else ""}",
                    backgroundColor = androidx.compose.ui.graphics.Color(0xFF9E9E9E)
                )
            }
        }

        // Details button with gray border and text
        androidx.compose.material3.OutlinedButton(
            onClick = onClick,
            modifier = Modifier.padding(start = 8.dp),
            border = BorderStroke(1.dp, androidx.compose.ui.graphics.Color(0xFF9E9E9E))
        ) {
            Text(context.getString(R.string.button_details), color = androidx.compose.ui.graphics.Color(0xFF9E9E9E))
        }
        }
    }
}

@Composable
fun CollectorBadgeChip(
    label: String,
    backgroundColor: androidx.compose.ui.graphics.Color
) {
    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = androidx.compose.ui.graphics.Color.White
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 600)
@Composable
fun CollectorListScreenPreview() {
    val sampleCollectors = listOf(
        Collector(
            id = 100,
            name = "Manolo Bellon",
            telephone = "3502457896",
            email = "manollo@caracol.com.co",
            image = "https://i.pravatar.cc/96?u=100",
            commentsCount = 1,
            favoritesCount = 1,
            albumsCount = 1
        ),
        Collector(
            id = 101,
            name = "Jaime Monsalve",
            telephone = "3012357936",
            email = "jmonsalve@rtvc.com.co",
            image = "https://i.pravatar.cc/96?u=101",
            commentsCount = 1,
            favoritesCount = 1,
            albumsCount = 1
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "👥 Coleccionistas", // Preview: usar string hardcodeado
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Buscar usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(24.dp),
            singleLine = true
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(sampleCollectors) { collector ->
                CollectorListItem(
                    collector = collector,
                    onClick = {}
                )
            }
        }
    }
}

