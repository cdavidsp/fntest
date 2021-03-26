package com.csosa.fntest.di

import com.csosa.fntest.di.provideFNTestApiService
import com.csosa.fntest.di.provideGoogleMapsApiService
import com.csosa.fntest.di.provideOkHttpClient
import com.csosa.fntest.di.provideRetrofit
import org.koin.dsl.module

val fakeNetworkModule = module {

    single { provideRetrofit(okHttpClient = get(), url = "http://localhost:8082/") }

    single { provideFNTestApiService(retrofit = get()) }

    single { provideGoogleMapsApiService(retrofit = get()) }

    single { provideOkHttpClient() }
}
