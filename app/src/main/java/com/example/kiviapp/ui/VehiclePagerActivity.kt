package com.example.kiviapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import com.example.kiviapp.R
import com.example.kiviapp.datamodel.Vehicle
import com.example.kiviapp.viewmodel.Status
import com.example.kiviapp.viewmodel.VehicleViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import org.koin.android.viewmodel.ext.android.viewModel

class VehiclePagerActivity : AppCompatActivity() {
    @BindView(R.id.viewpager)
    lateinit var viewPager: ViewPager2

    @BindView(R.id.tabLayout)
    lateinit var tabLayout: TabLayout

    @BindView(R.id.progress_circle)
    lateinit var progressView: ProgressBar

    private lateinit var vehicleList: List<Vehicle>
    private val viewModel by viewModel<VehicleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        val vehiclePagerAdapter = VehiclePagerAdapter(this, 2)
        viewPager.adapter = vehiclePagerAdapter

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
    }

    private fun getVehiclesList() {
        viewModel.loadVehicleData().observe(this, Observer { networkResource ->
            when (networkResource.status) {
                Status.LOADING -> {
                    // TODO: Progress bar
                    showProgress()
                    Log.d("LOADING", "Loading from network")
                }
                Status.SUCCESS -> {
                    hideProgress()
                    vehicleList = networkResource.data as List<Vehicle>
                }
                Status.ERROR -> {
                    Log.e("ERROR", "Error occured: Loading from network")
                }
            }
        })
    }

    fun showProgress() {
        progressView.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressView.visibility = View.GONE
    }
}
