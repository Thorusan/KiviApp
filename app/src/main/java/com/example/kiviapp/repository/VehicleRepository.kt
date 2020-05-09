package com.example.kiviapp.repository

import com.example.kiviapp.network.ApiService

class VehicleRepository (private val api: ApiService) {
    suspend fun getVehicles() = api.getVehicles()
}