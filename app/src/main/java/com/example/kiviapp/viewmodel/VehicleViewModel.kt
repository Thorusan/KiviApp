package com.example.kiviapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.kiviapp.repository.VehicleRepository
import kotlinx.coroutines.Dispatchers

class VehicleViewModel(private val vehicleRepository: VehicleRepository): ViewModel() {

    fun loadVehicleData() = liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val response = vehicleRepository.getVehicles()
        if(response.isSuccessful) {
            emit(Resource.success(response.body()))
        }
    }

    class ViewModelFactory(private var repository: VehicleRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return VehicleViewModel(repository) as T
        }
    }

}