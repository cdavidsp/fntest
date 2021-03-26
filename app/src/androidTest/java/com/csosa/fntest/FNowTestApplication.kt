package com.csosa.fntest

import android.app.Application
import com.csosa.fntest.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FNowTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FNowTestApplication)
            modules(
                fakeNetworkModule,
                appPreferenceModule,
                fakeDataSourceModule,
                useCasesModule,
                viewModelsModule
            )
        }
    }
}
