package com.example.kiviapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import com.example.kiviapp.R
import com.example.kiviapp.common.Utility
import com.example.kiviapp.datamodel.Vehicle
import com.example.kiviapp.datamodel.VehicleType
import com.example.kiviapp.viewmodel.Status
import com.example.kiviapp.viewmodel.VehicleViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class VehiclePagerActivity : AppCompatActivity() {
    @BindView(R.id.viewpager)
    lateinit var viewPager: ViewPager2

    @BindView(R.id.tabLayout)
    lateinit var tabLayout: TabLayout

    @BindView(R.id.progress_circle)
    lateinit var progressView: ProgressBar

    @BindView(R.id.container)
    lateinit var view: View

    @BindView(R.id.btn_scan)
    lateinit var btnScan: Button

    private lateinit var vehicleList: List<Vehicle>
    private val viewModel by viewModel<VehicleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        checkConnectionAndShowData()

    }

    private fun checkConnectionAndShowData() {
        if (Utility.isNetworkAvailable(this)) {
            getVehiclesList()
        } else {
            showSnackbarNoInternet()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                    openSoundActivity()
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private fun getVehiclesList() {
        viewModel.loadVehicleData().observe(this, Observer { networkResource ->
            when (networkResource.status) {
                Status.LOADING -> {
                    showProgress()
                    Log.d("LOADING", "Loading from network")
                }
                Status.SUCCESS -> {
                    hideProgress()

                    @Suppress("UNCHECKED_CAST")
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

        setTabs()

        registerListeners();
    }

    private fun setTabs() {
        TabLayoutMediator(
            tabLayout,
            viewPager,
            TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = VehicleType.CAR.toString()
                    1 -> tab.text = VehicleType.MOTORCYCLE.toString()
                }
            }).attach()
    }

    private fun registerListeners() {
        btnScan.setOnClickListener {
            startScanning()
        }
    }

    private fun startScanning() {
        val scanner = IntentIntegrator(this)
        scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        scanner.setBeepEnabled(true)
        scanner.setCaptureActivity(CustomerScannerActivity::class.java)
        scanner.initiateScan()
    }

    private fun openSoundActivity() {
        val intent = Intent(this, SoundActivity::class.java)
        startActivity(intent)
    }

    fun showProgress() {
        progressView.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressView.visibility = View.GONE
    }

    fun showSnackbarNoInternet() {
        val snack = Snackbar.make(container,
            getString(R.string.error_internet_connection),
            Snackbar.LENGTH_INDEFINITE)
        snack.setAction(getString(R.string.try_again), {
            checkConnectionAndShowData()
        })
        snack.show()
    }
}
