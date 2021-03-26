package com.csosa.fntest.fakes

import com.csosa.fntest.domain.model.Vehicle
import com.csosa.fntest.domain.usecases.GetAllVehiclesBaseUseCase
import com.csosa.fntest.domain.usecases.GetAllVehiclesUseCaseInput
import com.csosa.fntest.utils.Data
import com.csosa.fntest.utils.UiState
import kotlinx.coroutines.flow.Flow

class FakeGetAllVehiclesUseCase(
    uiState: UiState
) : BaseTestUseCase<List<Vehicle>, GetAllVehiclesUseCaseInput>(uiState), GetAllVehiclesBaseUseCase {

    override fun getValue(params: GetAllVehiclesUseCaseInput): List<Vehicle> = Data.vehicles

    override suspend fun invoke(params: GetAllVehiclesUseCaseInput): Flow<List<Vehicle>> = execute(params)
}
