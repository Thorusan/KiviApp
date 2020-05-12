package com.example.kiviapp.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.example.kiviapp.R
import com.example.kiviapp.datamodel.Vehicle
import com.example.kiviapp.datamodel.VehicleType
import java.util.*
import java.util.Collections.shuffle
import kotlin.collections.ArrayList


class VehicleFragment : Fragment() {
    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView
    @BindView(R.id.swipeRefreshLayout)
    lateinit var swipeRefresh: SwipeRefreshLayout

    private var vehicleList: List<Vehicle>? = null
    private lateinit var adapter: VehicleViewAdapter

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_VEHICLE_LIST = "vehicle_list"

        fun getInstance(position: Int, vehiclelist: ArrayList<Vehicle>): Fragment {
            val vehicleFragment = VehicleFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            bundle.putParcelableArrayList(ARG_VEHICLE_LIST, vehiclelist)
            vehicleFragment.arguments = bundle
            return vehicleFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_vehicle, container, false)
        ButterKnife.bind(this, view);

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = requireArguments().getInt(ARG_POSITION)
        vehicleList =  requireArguments().getParcelableArrayList(ARG_VEHICLE_LIST)

        adapter = VehicleViewAdapter(groupByType(position))
        recyclerView.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed({
                shuffle(position)
            }, 1000)
        }
    }

    private fun groupByType(position: Int): List<Vehicle> {
        lateinit var vehicleListGroupedByType: List<Vehicle>
        when (position) {
            0 -> vehicleListGroupedByType = vehicleList!!.filter { it.type == VehicleType.CAR }
            1 -> vehicleListGroupedByType =
                vehicleList!!.filter { it.type == VehicleType.MOTORCYCLE }
        }
        return vehicleListGroupedByType
    }

    private fun shuffle(position: Int) {
        shuffle(vehicleList)
        adapter.refreshData(groupByType(position))
        swipeRefresh.isRefreshing = false;
    }
}

