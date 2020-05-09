package com.example.kiviapp.network

import com.example.kiviapp.common.Constants.Companion.BASE_URL
import com.example.kiviapp.datamodel.Vehicle
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getVehicles(): Response<List<Vehicle>>

    @GET("/")
    fun getVehiclesTest(): Deferred<List<Vehicle>>


    companion object {
        fun create(): ApiService {
            val interceptorHttpLogging = HttpLoggingInterceptor()
            interceptorHttpLogging.level = HttpLoggingInterceptor.Level.BODY

            val interceptorMockData =
                MockDataInterceptor()

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptorHttpLogging)
                .addInterceptor(interceptorMockData)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client) // Http logging of requests and response
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

}