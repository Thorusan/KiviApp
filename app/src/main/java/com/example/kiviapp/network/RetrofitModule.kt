package com.example.kiviapp.network

import com.example.kiviapp.common.Constants
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiServiceModule = module {
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    single { provideApiService(get()) }
}


val networkModule = module {
    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }

    fun provideMockingInterceptor(): MockDataInterceptor {
        return MockDataInterceptor()
    }

    fun provideOkHttpClient(
        interceptorHttpLogging: HttpLoggingInterceptor,
        interceptorMockData: MockDataInterceptor
    ): OkHttpClient {
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

    fun provideResponseHandler(): ResponseHandler {
        return ResponseHandler()
    }

    single { provideLoggingInterceptor() }
    single { provideMockingInterceptor() }
    single { provideResponseHandler() }
    single { provideOkHttpClient(get(), get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
}