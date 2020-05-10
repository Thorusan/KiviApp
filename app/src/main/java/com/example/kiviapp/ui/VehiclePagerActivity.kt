package com.example.kiviapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import com.example.kiviapp.R
import com.example.kiviapp.datamodel.Vehicle
import com.example.kiviapp.datamodel.VehicleType
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

    @BindView(R.id.btn_scan)
    lateinit var btnScan: Button

    private lateinit var vehicleList: List<Vehicle>
    private val viewModel by viewModel<VehicleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

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
                    initUi();
                }
                Status.ERROR -> {
                    Log.e("ERROR", "Error occured: Loading from network")
                }
            }
        })
    }

    private fun initUi() {
        val vehiclePagerAdapter = VehiclePagerAdapter(this, VehicleType.values().size, vehicleList)
        viewPager.adapter = vehiclePagerAdapter

        TabLayoutMediator(
            tabLayout,
            viewPager,
            TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = VehicleType.CAR.toString()
                    1 -> tab.text = VehicleType.MOTORCYCLE.toString()
                }
            }).attach()

        registerListeners();
    }

    private fun registerListeners() {
        btnScan.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
        }

    }

    fun showProgress() {
        progressView.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressView.visibility = View.GONE
    }
}
