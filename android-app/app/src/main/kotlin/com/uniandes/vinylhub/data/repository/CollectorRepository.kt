package com.uniandes.vinylhub.data.repository

import com.uniandes.vinylhub.data.local.CollectorDao
import com.uniandes.vinylhub.data.model.Collector
import com.uniandes.vinylhub.data.remote.CollectorService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectorRepository @Inject constructor(
    private val collectorService: CollectorService,
    private val collectorDao: CollectorDao
) {
    
    fun getAllCollectors(): Flow<List<Collector>> = collectorDao.getAllCollectors()
    
    suspend fun getCollectorById(id: Int): Collector? = collectorDao.getCollectorById(id)
    
    suspend fun refreshCollectors() {
        try {
            val collectors = collectorService.getAllCollectors()
            collectorDao.insertCollectors(collectors)
        } catch (e: Exception) {
            // Handle error
        }
    }
    
    suspend fun insertCollector(collector: Collector) {
        collectorDao.insertCollector(collector)
    }
    
    suspend fun updateCollector(collector: Collector) {
        collectorDao.updateCollector(collector)
    }
    
    suspend fun deleteCollector(collector: Collector) {
        collectorDao.deleteCollector(collector)
    }
}

