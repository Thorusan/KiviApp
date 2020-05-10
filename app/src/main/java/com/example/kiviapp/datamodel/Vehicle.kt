package com.example.kiviapp.datamodel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Vehicle(
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("type")
    val type: VehicleType?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()?.let { VehicleType.valueOf(it) }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(brand)
        parcel.writeString(model)
        parcel.writeString(type?.name);
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vehicle> {
        override fun createFromParcel(parcel: Parcel): Vehicle {
            return Vehicle(parcel)
        }

        override fun newArray(size: Int): Array<Vehicle?> {
            return arrayOfNulls(size)
        }
    }
}





