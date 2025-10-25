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

    suspend fun getCollectorById(id: Int): Collector? {
        try {
            Log.d("CollectorRepository", "Obteniendo coleccionista $id del API...")
            val apiCollector = collectorService.getCollectorById(id)

            // Convertir a Collector con datos completos
            val collector = Collector(
                id = apiCollector.id,
                name = apiCollector.name ?: "",
                telephone = apiCollector.telephone ?: "",
                email = apiCollector.email ?: "",
                image = apiCollector.image,
                commentsCount = apiCollector.comments?.size ?: 0,
                favoritesCount = apiCollector.favoritePerformers?.size ?: 0,
                albumsCount = apiCollector.collectorAlbums?.size ?: 0
            ).apply {
                comments = apiCollector.comments ?: emptyList()
                favoritePerformers = apiCollector.favoritePerformers ?: emptyList()
                collectorAlbums = apiCollector.collectorAlbums ?: emptyList()
            }

            Log.d("CollectorRepository", "Coleccionista $id obtenido: comments=${collector.comments.size}, favorites=${collector.favoritePerformers.size}, albums=${collector.collectorAlbums.size}")
            return collector
        } catch (e: Exception) {
            Log.e("CollectorRepository", "Error obteniendo coleccionista $id: ${e.message}", e)
            return null
        }
    }

    suspend fun refreshCollectors() {
        try {
            val apiCollectors = collectorService.getAllCollectors()
            val collectors = apiCollectors.map { apiCollector ->
                Collector(
                    id = apiCollector.id,
                    name = apiCollector.name ?: "",
                    telephone = apiCollector.telephone ?: "",
                    email = apiCollector.email ?: "",
                    image = apiCollector.image,
                    commentsCount = apiCollector.comments?.size ?: 0,
                    favoritesCount = apiCollector.favoritePerformers?.size ?: 0,
                    albumsCount = apiCollector.collectorAlbums?.size ?: 0
                ).apply {
                    comments = apiCollector.comments ?: emptyList()
                    favoritePerformers = apiCollector.favoritePerformers ?: emptyList()
                    collectorAlbums = apiCollector.collectorAlbums ?: emptyList()
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

