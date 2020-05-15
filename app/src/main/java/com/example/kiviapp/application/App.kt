package com.example.kiviapp.application

import android.app.Application
import com.example.kiviapp.di.apiModule
import com.example.kiviapp.di.repositoryModule
import com.example.kiviapp.di.networkModule
import com.example.kiviapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class App : Application() {

    @Override
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(repositoryModule, viewModelModule, networkModule, apiModule))
        }
    }
}