package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uniandes.vinylhub.data.model.Collector
import com.uniandes.vinylhub.presentation.viewmodel.CollectorViewModel

@Composable
fun CollectorListScreen(
    viewModel: CollectorViewModel,
    onCollectorClick: (Int) -> Unit
) {
    val collectors by viewModel.collectors.collectAsState(initial = emptyList())
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Coleccionistas",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (collectors.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                items(collectors) { collector ->
                    CollectorListItem(
                        collector = collector,
                        onClick = { onCollectorClick(collector.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun CollectorListItem(
    collector: Collector,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = collector.name)
        Text(text = "Teléfono: ${collector.telephone}")
        Text(text = "Email: ${collector.email}")
    }
}

@Preview(showBackground = true)
@Composable
fun CollectorListItemPreview() {
    val sampleCollector = Collector(
        id = 1,
        name = "Jaime Andrés Monsalve",
        telephone = "3102178976",
        email = "j.monsalve@gmail.com"
    )
    CollectorListItem(collector = sampleCollector, onClick = {})
}

