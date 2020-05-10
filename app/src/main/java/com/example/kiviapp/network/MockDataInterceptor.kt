package com.example.kiviapp.network

import com.example.kiviapp.BuildConfig
import com.example.kiviapp.datamodel.Vehicle
import com.example.kiviapp.datamodel.VehicleType
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

internal class MockDataInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response?
        if (BuildConfig.DEBUG) {
            val responseString: String = getVehiclesJson()

            response = Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            response = chain.proceed(chain.request())
        }
        return response
    }

    private fun getVehiclesJson(): String {
        val vehiclesList: ArrayList<Vehicle> = ArrayList();
        vehiclesList.add(Vehicle("Audi", "A3", VehicleType.CAR));
        vehiclesList.add(Vehicle("Audi", "A4", VehicleType.CAR));
        vehiclesList.add(Vehicle("Audi", "A8", VehicleType.CAR));
        vehiclesList.add(Vehicle("Audi", "Q2", VehicleType.CAR));
        vehiclesList.add(Vehicle("BMW", "323i", VehicleType.CAR));
        vehiclesList.add(Vehicle("BMW", "105", VehicleType.CAR));
        vehiclesList.add(Vehicle("BMW", "106", VehicleType.CAR));
        vehiclesList.add(Vehicle("BMW", "107", VehicleType.CAR));
        vehiclesList.add(Vehicle("BMW", "108", VehicleType.CAR));
        vehiclesList.add(Vehicle("Cadillac", "XTS", VehicleType.CAR));
        vehiclesList.add(Vehicle("Cadillac", "XLR", VehicleType.CAR));
        vehiclesList.add(Vehicle("Honda", "CM-70", VehicleType.MOTORCYCLE));
        vehiclesList.add(Vehicle("Honda", "VTX 1300", VehicleType.MOTORCYCLE));
        vehiclesList.add(Vehicle("Honda", "X 11", VehicleType.MOTORCYCLE));
        vehiclesList.add(Vehicle("Honda", "X8R", VehicleType.MOTORCYCLE));
        vehiclesList.add(Vehicle("Honda", "XR 125", VehicleType.MOTORCYCLE));
        vehiclesList.add(Vehicle("Honda", "CBR 125", VehicleType.MOTORCYCLE));
        vehiclesList.add(Vehicle("Honda", "FMX 650", VehicleType.MOTORCYCLE));
        vehiclesList.add(Vehicle("Honda", "Forza", VehicleType.MOTORCYCLE));
        vehiclesList.add(Vehicle("Honda", "CBF 1000", VehicleType.MOTORCYCLE));

        return Gson().toJson(vehiclesList);
    }


}