package com.example.kiviapp.di

import com.example.kiviapp.common.Constants
import com.example.kiviapp.network.ApiService
import com.example.kiviapp.network.MockDataInterceptor
import com.example.kiviapp.network.ResponseHandler
import com.example.kiviapp.repository.VehicleRepository
import com.example.kiviapp.viewmodel.VehicleViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel {
        VehicleViewModel(get())
    }
}

val repositoryModule = module {
    single {
        VehicleRepository(get(),get())
    }
}

val apiModule = module {
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    single { provideApiService(get()) }
}

val networkModule = module {
    factory { ResponseHandler() }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideHttpClient(): OkHttpClient {
        val interceptorHttpLogging = HttpLoggingInterceptor()
        interceptorHttpLogging.level = HttpLoggingInterceptor.Level.BODY

        val interceptorMockData = MockDataInterceptor()

        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptorHttpLogging)
            .addInterceptor(interceptorMockData)
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
}