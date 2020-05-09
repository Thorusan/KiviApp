package com.example.kiviapp.repository

import com.example.kiviapp.datamodel.Vehicle
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    //@GET("/vehicles.json")
    @GET("/posts")
    fun getVehicles(): Deferred<List<Vehicle>>


    companion object {
        fun create(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .client(client) // Http logging of requests and response
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

}