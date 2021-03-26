package com.csosa.fntest

import android.app.Application
import com.csosa.fntest.di.*
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class FNowApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)

        startKoin {
            androidContext(this@FNowApplication)
            modules(
                    networkModule,
                    viewModelsModule,
                    appPreferenceModule,
                    dataSourceModule,
                    useCasesModule
            )
        }

    }

}
