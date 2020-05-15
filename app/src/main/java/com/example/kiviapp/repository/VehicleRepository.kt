package com.example.kiviapp.repository

import com.example.kiviapp.datamodel.Vehicle
import com.example.kiviapp.network.ApiService
import com.example.kiviapp.network.ResponseHandler
import com.example.kiviapp.viewmodel.Resource

class VehicleRepository (
    private val api: ApiService,
    private val responseHandler: ResponseHandler
) {
    suspend fun getVehicles(): Resource<List<Vehicle>> {
        return try {
            val response = api.getVehicles()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}