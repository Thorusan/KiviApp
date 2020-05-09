package com.example.kiviapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import com.example.kiviapp.R
import com.example.kiviapp.application.App
import com.example.kiviapp.datamodel.Vehicle
import com.example.kiviapp.repository.ApiService
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


        val service = App.apiService!!
        callapi_getVehicles(service)
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }

    private fun callapi_getVehicles(service: ApiService) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = service.getVehicles()
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
