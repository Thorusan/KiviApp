package com.example.album.datamodel

import com.example.kiviapp.datamodel.VehicleType
import com.google.gson.annotations.SerializedName

data class Vehicle(
    @SerializedName("model")
    val brand: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("type")
    val id: VehicleType.VehicleType


)





