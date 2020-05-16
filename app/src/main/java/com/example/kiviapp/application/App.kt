package com.example.kiviapp.application

import android.app.Application
import com.example.kiviapp.network.apiServiceModule
import com.example.kiviapp.network.networkModule
import com.example.kiviapp.repository.repositoryModule
import com.example.kiviapp.viewmodel.viewModelModule
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
            modules(listOf(repositoryModule, viewModelModule,
                networkModule,
                apiServiceModule
            ))
        }
    }
}