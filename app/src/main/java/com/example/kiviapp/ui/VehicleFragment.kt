package com.example.kiviapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kiviapp.R
import kotlinx.android.synthetic.main.fragment_vehicle.*

class VehicleFragment : Fragment() {

    companion object {
        const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val vehicleFragment = VehicleFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            vehicleFragment.arguments = bundle
            return vehicleFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = requireArguments().getInt(ARG_POSITION)

        test.text = "test"
    }


}

