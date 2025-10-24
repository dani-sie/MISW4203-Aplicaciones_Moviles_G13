package com.uniandes.vinylhub.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uniandes.vinylhub.data.model.Collector
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectorDao {
    
    @Query("SELECT * FROM collectors")
    fun getAllCollectors(): Flow<List<Collector>>
    
    @Query("SELECT * FROM collectors WHERE id = :id")
    suspend fun getCollectorById(id: Int): Collector?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollectors(collectors: List<Collector>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollector(collector: Collector)
    
    @Update
    suspend fun updateCollector(collector: Collector)
    
    @Delete
    suspend fun deleteCollector(collector: Collector)
    
    @Query("DELETE FROM collectors")
    suspend fun deleteAllCollectors()
}

