package com.example.kiviapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.kiviapp.repository.VehicleRepository
import kotlinx.coroutines.Dispatchers

class VehicleViewModel(private val vehicleRepository: VehicleRepository) : ViewModel() {
    fun loadVehicleData() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        emit(vehicleRepository.getVehicles())
    }
}