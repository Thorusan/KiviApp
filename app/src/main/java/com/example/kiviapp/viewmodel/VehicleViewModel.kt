package com.example.kiviapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.kiviapp.repository.VehicleRepository
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        VehicleViewModel(get())
    }
}

class VehicleViewModel(private val vehicleRepository: VehicleRepository) : ViewModel() {
    fun loadVehicleData() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        emit(vehicleRepository.getVehicles())
    }
}