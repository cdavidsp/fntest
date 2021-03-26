package com.csosa.fntest.di

import android.content.Context
import com.csosa.fntest.data.preferences.AppPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val appPreferenceModule = module {

    single { provideAppPreference(context = androidContext()) }
}

internal fun provideAppPreference(context: Context): AppPreferences = AppPreferences(context)
