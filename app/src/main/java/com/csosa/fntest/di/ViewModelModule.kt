package com.csosa.fntest.di

import com.csosa.fntest.viewmodel.ListVehiclesViewModel
import com.csosa.fntest.viewmodel.MapsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel {
        ListVehiclesViewModel(
            getAllVehiclesUseCase = get(named("vehicles"))
        )
    }

    viewModel {
        MapsViewModel(
            getAllVehiclesUseCase = get(named("vehicles"))
        )
    }

}
