package com.example.kiviapp.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kiviapp.datamodel.Vehicle

class VehiclePagerAdapter(
    activity: AppCompatActivity,
    val itemsCount: Int,
    val vehicleList: List<Vehicle>
) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return VehicleFragment.getInstance(position, ArrayList(vehicleList))
    }
}