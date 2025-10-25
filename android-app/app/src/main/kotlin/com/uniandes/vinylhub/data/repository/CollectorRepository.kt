package com.uniandes.vinylhub.data.repository

import android.util.Log
import com.uniandes.vinylhub.data.local.CollectorDao
import com.uniandes.vinylhub.data.model.Collector
import com.uniandes.vinylhub.data.remote.CollectorService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transformLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class CollectorRepository @Inject constructor(
    private val collectorService: CollectorService,
    private val collectorDao: CollectorDao
) {
    private var collectorsInMemory: Map<Int, Collector> = emptyMap()
    private var isInitialized = false

    fun getAllCollectors(): Flow<List<Collector>> = collectorDao.getAllCollectors()
        .transformLatest { dbCollectors ->
            if (!isInitialized) {
                try {
                    refreshCollectors()
                } catch (e: Exception) {
                    Log.e("CollectorRepository", "Error loading collectors: ${e.message}")
                }
            }
            val enrichedCollectors = dbCollectors.map { dbCollector ->
                collectorsInMemory[dbCollector.id] ?: dbCollector
            }
            emit(enrichedCollectors)
        }

    suspend fun getCollectorById(id: Int): Collector? = collectorDao.getCollectorById(id)

    suspend fun refreshCollectors() {
        try {
            val apiCollectors = collectorService.getAllCollectors()
            val collectors = apiCollectors.map { apiCollector ->
                Collector(
                    id = apiCollector.id,
                    name = apiCollector.name,
                    telephone = apiCollector.telephone,
                    email = apiCollector.email,
                    image = apiCollector.image,
                    commentsCount = apiCollector.comments.size,
                    favoritesCount = apiCollector.favoritePerformers.size,
                    albumsCount = apiCollector.collectorAlbums.size
                ).apply {
                    comments = apiCollector.comments
                    favoritePerformers = apiCollector.favoritePerformers
                    collectorAlbums = apiCollector.collectorAlbums
                }
            }
            collectorsInMemory = collectors.associateBy { it.id }
            isInitialized = true
            collectorDao.insertCollectors(collectors)
        } catch (e: Exception) {
            Log.e("CollectorRepository", "Error refreshing collectors: ${e.message}", e)
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

