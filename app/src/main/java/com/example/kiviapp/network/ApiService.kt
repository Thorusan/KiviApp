package com.example.kiviapp.network

import com.example.kiviapp.datamodel.Vehicle
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getVehicles(): List<Vehicle>
}