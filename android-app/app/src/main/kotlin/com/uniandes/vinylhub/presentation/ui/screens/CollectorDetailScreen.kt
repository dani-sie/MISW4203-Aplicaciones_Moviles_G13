package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uniandes.vinylhub.data.model.Collector

@Composable
fun CollectorDetailScreen(
    collector: Collector,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = collector.name,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(text = "Teléfono: ${collector.telephone}")
        Text(text = "Email: ${collector.email}")
    }
}

