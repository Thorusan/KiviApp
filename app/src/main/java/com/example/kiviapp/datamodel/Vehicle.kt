package com.example.kiviapp.datamodel

import com.google.gson.annotations.SerializedName

data class Vehicle(
    /*val userId: Int,
    val id: Int,
    val title: String,
    val body: String*/
    @SerializedName("brand")
    val brand: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("type")
    val type: VehicleType

)





