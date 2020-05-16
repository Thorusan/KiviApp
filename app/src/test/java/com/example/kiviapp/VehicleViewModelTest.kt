package com.example.kiviapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.kiviapp.datamodel.Vehicle
import com.example.kiviapp.datamodel.VehicleType
import com.example.kiviapp.repository.VehicleRepository
import com.example.kiviapp.viewmodel.Resource
import com.example.kiviapp.viewmodel.VehicleViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.timeout
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class VehicleViewModelTest {
    private lateinit var viewModel: VehicleViewModel
    private lateinit var vehicleRepository: VehicleRepository

    private val vehicleList = listOf(
        Vehicle("Audi", "A3", VehicleType.CAR),
        Vehicle("Audi", "A4", VehicleType.CAR),
        Vehicle("Audi", "A8", VehicleType.CAR)
    )
    private val successResource = Resource.success(vehicleList)

    private lateinit var vehicleObserver: Observer<Resource<List<Vehicle>>>

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        vehicleRepository = mock()
        runBlocking {
            whenever(vehicleRepository.getVehicles()).thenReturn(successResource)
        }
        viewModel = VehicleViewModel(vehicleRepository)
        vehicleObserver = mock()
    }

    @Test
    fun `when loadVehicleData is called, then observer is updated with success`() = runBlocking {
        //viewModel.loadVehicleData().observeForever(vehicleObserver)
        delay(10)
        verify(vehicleObserver, timeout(50)).onChanged(Resource.loading(null))
        verify(vehicleObserver, timeout(50)).onChanged(successResource)
    }
}
