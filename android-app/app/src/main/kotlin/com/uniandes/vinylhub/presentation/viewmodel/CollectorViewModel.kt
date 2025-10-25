package com.uniandes.vinylhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uniandes.vinylhub.data.model.Collector
import com.uniandes.vinylhub.data.repository.CollectorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CollectorViewModel @Inject constructor(
    private val collectorRepository: CollectorRepository
) : ViewModel() {

    val collectors: Flow<List<Collector>> = collectorRepository.getAllCollectors()

    init {
        refreshCollectors()
    }

    fun refreshCollectors() {
        viewModelScope.launch {
            collectorRepository.refreshCollectors()
        }
    }

    suspend fun getCollectorById(id: Int): Collector? {
        return collectorRepository.getCollectorById(id)
    }
}

