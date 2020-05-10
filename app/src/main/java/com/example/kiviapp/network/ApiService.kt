package com.example.kiviapp.network

import com.example.kiviapp.datamodel.Vehicle
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getVehicles(): Response<List<Vehicle>>
}