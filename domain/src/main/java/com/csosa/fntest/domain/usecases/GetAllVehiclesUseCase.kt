package com.csosa.fntest.domain.usecases

import com.csosa.fntest.domain.model.Bounds
import com.csosa.fntest.domain.model.Coordinate
import com.csosa.fntest.domain.model.Vehicle
import com.csosa.fntest.domain.respository.IVehiclesRepository
import kotlinx.coroutines.flow.Flow

typealias GetAllVehiclesBaseUseCase = BaseUseCase<GetAllVehiclesUseCaseInput, Flow<List<Vehicle>>>

class GetAllVehiclesUseCase(
    private val vehiclesRepository: IVehiclesRepository
) : GetAllVehiclesBaseUseCase {

    override suspend fun invoke(params: GetAllVehiclesUseCaseInput)
            = vehiclesRepository.getVehiclesbyBounds(params.apiKey, params.location, params.bounds)

}
class GetAllVehiclesUseCaseInput(
    val bounds: Bounds,
    val location: Coordinate,
    val apiKey: String
)
