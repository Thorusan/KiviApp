package com.example.kiviapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import com.example.kiviapp.R
import com.example.kiviapp.application.App
import com.example.kiviapp.datamodel.Vehicle
import com.example.kiviapp.network.ApiService
import com.example.kiviapp.repository.VehicleRepository
import com.example.kiviapp.viewmodel.Status
import com.example.kiviapp.viewmodel.VehicleViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import kotlinx.coroutines.*
import retrofit2.HttpException

class ViewPagerActivity : AppCompatActivity() {
    @BindView(R.id.viewpager)
    lateinit var viewPager: ViewPager2

    @BindView(R.id.tabLayout)
    lateinit var tabLayout: TabLayout

    private var vehicles: List<Vehicle>? = null

    private var job: Job? = null
    private lateinit var viewModel: VehicleViewModel
    private val service = App.apiService!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        viewPager.adapter = ViewPagerAdapter()

        TabLayoutMediator(
            tabLayout,
            viewPager,
            TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = "Car"
                    1 -> tab.text = "Motorcycle"
                }
            }).attach()

        getVehiclesList()

        //callapi_getVehicles_Text(service)
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }

    private fun getVehiclesList() {
        viewModel = ViewModelProvider(this, VehicleViewModel.ViewModelFactory(VehicleRepository(service)))
            .get(VehicleViewModel::class.java)
        viewModel.loadVehicleData().observe(this, Observer { networkResource ->
            when (networkResource.status) {
                Status.LOADING -> {
                    //message.text = "loading data from network"
                    Log.d("LOADING", "Loading from network")
                }
                Status.SUCCESS -> {
                    val vehicleList = networkResource.data
                }
                Status.ERROR -> {
                    Log.e("ERROR", "Error occured: Loading from network")
                }
            }
        })
    }

    private fun callapi_getVehicles_Text(service: ApiService) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = service.getVehiclesTest()
            withContext(Dispatchers.Main) {
                try {
                    vehicles = response.await()
                } catch (e: HttpException) {
                    Log.e("REQUEST", "Exception ${e.message}")
                } catch (e: Throwable) {
                    Log.e("REQUEST", "Some other thing went wrong.")
                }
            }
        }
    }
}
