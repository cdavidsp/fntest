package com.csosa.fntest.di

import androidx.room.Room
import com.csosa.fntest.data.local.FNTestDatabase
import com.csosa.fntest.data.local.dao.VehiclesDao
import com.csosa.fntest.data.local.dao.VehiclesQueriesDao
import com.csosa.fntest.data.respository.VehiclesRepository
import com.csosa.fntest.domain.respository.IVehiclesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSourceModule = module {

    single<IVehiclesRepository> { VehiclesRepository(
        fNTestApiService = get(),
        googleMapsApiService = get(),
        appPreferences = get(),
        vehiclesDao = get(),
        vehiclesQueriesDao = get()
    )
    }

    single {
        Room
            .databaseBuilder(androidContext(), FNTestDatabase::class.java, "fntest_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { provideVehiclesDao(db = get()) }
    single { provideVehiclesQueriesDao(db = get()) }
}

internal fun provideVehiclesDao(db: FNTestDatabase): VehiclesDao = db.vehiclesDao()

internal fun provideVehiclesQueriesDao(db: FNTestDatabase): VehiclesQueriesDao = db.vehicleQueryDao()
