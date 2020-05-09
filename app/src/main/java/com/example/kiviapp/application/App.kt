package com.example.kiviapp.application

import android.app.Application
import com.example.kiviapp.repository.ApiService

val apiservice by lazy {
    ApiService.create()
}

class App : Application() {
    companion object {
        var apiService: ApiService? = apiservice
    }

    @Override
    override fun onCreate() {
        super.onCreate()
    }
}