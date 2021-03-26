package com.csosa.fntest.di

import com.csosa.fntest.data.remote.api.FNTestApiService
import com.csosa.fntest.data.remote.api.GoogleMapsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { provideFNTestApiService(retrofit = get(named("my-taxi"))) }

    single { provideGoogleMapsApiService(retrofit = get(named("google-apis"))) }

    single(named("my-taxi")) { provideRetrofit(okHttpClient = get(), url = "https://fake-poi-api.mytaxi.com/") }

    single(named("google-apis")) { provideRetrofit(okHttpClient = get(), url = "https://maps.googleapis.com/") }

    single { provideOkHttpClient() }
}

internal fun provideOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

internal fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

internal fun provideFNTestApiService(retrofit: Retrofit): FNTestApiService =
    retrofit.create(FNTestApiService::class.java)

internal fun provideGoogleMapsApiService(retrofit: Retrofit): GoogleMapsApiService =
    retrofit.create(GoogleMapsApiService::class.java)
