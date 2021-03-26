package com.csosa.fntest.di

import com.csosa.fntest.domain.respository.IVehiclesRepository
import com.csosa.fntest.domain.usecases.*
import org.koin.core.qualifier.named
import org.koin.dsl.module


val useCasesModule = module {

    single(named("vehicles")) { provideGetAllVehiclesUseCase(get()) }

}

fun provideGetAllVehiclesUseCase(vehiclesRepository: IVehiclesRepository): GetAllVehiclesBaseUseCase {
    return GetAllVehiclesUseCase(vehiclesRepository)
}

