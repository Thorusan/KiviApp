package com.example.kiviapp.datamodel

import androidx.annotation.StringDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class VehicleType(@param:VehicleType private var vehicleType: String) {
    @VehicleType
    override fun toString(): String {
        return vehicleType
    }

    fun setVehicleType(@VehicleType vehicleType: String) {
        this.vehicleType = vehicleType
    }

    @StringDef(CAR, MOTORCYCLE)
    @Retention(RetentionPolicy.SOURCE)
    annotation class VehicleType

    companion object {
        const val CAR = "car"
        const val MOTORCYCLE = "motorcycle"
    }

}
