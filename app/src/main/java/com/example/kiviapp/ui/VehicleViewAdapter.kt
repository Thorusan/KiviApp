package com.example.kiviapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.kiviapp.R
import com.example.kiviapp.datamodel.Vehicle

class VehicleViewAdapter(private val vehicleList: List<Vehicle>) :
    RecyclerView.Adapter<VehicleViewAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_vehicle, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(vehicleList[position])
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.text_brand)
        lateinit var textBrand: TextView

        @BindView(R.id.text_model)
        lateinit var textModel: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(vehicleItem: Vehicle) = with(itemView) {
            textBrand.text = vehicleItem.brand
            textModel.text = vehicleItem.model
        }
    }
}