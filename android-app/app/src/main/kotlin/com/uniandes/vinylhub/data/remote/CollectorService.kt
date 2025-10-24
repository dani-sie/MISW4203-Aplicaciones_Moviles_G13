package com.uniandes.vinylhub.data.remote

import com.uniandes.vinylhub.data.model.Collector
import retrofit2.http.GET
import retrofit2.http.Path

interface CollectorService {
    
    @GET("collectors")
    suspend fun getAllCollectors(): List<Collector>
    
    @GET("collectors/{id}")
    suspend fun getCollectorById(@Path("id") id: Int): Collector
}

