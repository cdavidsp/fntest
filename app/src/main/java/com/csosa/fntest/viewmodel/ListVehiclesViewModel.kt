package com.csosa.fntest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.csosa.fntest.commons.ExceptionHandler
import com.csosa.fntest.commons.SampleData
import com.csosa.fntest.domain.model.Bounds
import com.csosa.fntest.domain.model.Coordinate
import com.csosa.fntest.domain.usecases.GetAllVehiclesBaseUseCase
import com.csosa.fntest.domain.usecases.GetAllVehiclesUseCaseInput
import com.csosa.fntest.idlingresource.EspressoIdlingResource
import com.csosa.fntest.mappers.toPresentation
import com.csosa.fntest.models.VehiclePresentation
import com.csosa.fntest.models.states.Error
import com.csosa.fntest.models.states.ListVehiclesViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

internal class ListVehiclesViewModel(
    private val getAllVehiclesUseCase: GetAllVehiclesBaseUseCase
) : BaseViewModel() {

    // region Members

    private var getAllVehicleJob: Job? = null

    val vehiclesViewState: LiveData<ListVehiclesViewState>
        get() = _vehicleViewState

    private var _vehicleViewState = MutableLiveData<ListVehiclesViewState>()

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        _vehicleViewState.value =
            _vehicleViewState.value?.copy(isLoading = false, error = Error(message))
    }

    // endregion

    // region Constructors

    init {
        _vehicleViewState.value =
            ListVehiclesViewState(isLoading = true, error = null, vehicles = null)

    }

    // endregion

    // region Android API

    override fun onCleared() {
        super.onCleared()
        getAllVehicleJob?.cancel()
    }

    // endregion

    // region Public API

    fun executeGetAllVehicles(apiKey: String) {
        EspressoIdlingResource.increment()

        getAllVehicleJob?.cancel()
        getAllVehicleJob = launchCoroutine {
            delay(500)
            onVehiclesLoading()
            getAllVehiclesUseCase(GetAllVehiclesUseCaseInput( SampleData.bounds, SampleData.bounds.center, apiKey)).collect { vehicles ->

                val vehiclesPresentation = vehicles.map { it.toPresentation() }
                onVehiclesLoadingComplete(vehiclesPresentation)
            }
        }
    }

    // endregion

    // region Private API

    private fun onVehiclesLoading() {

        EspressoIdlingResource.increment()
        _vehicleViewState.value = _vehicleViewState.value?.copy(isLoading = true)
    }

    private fun onVehiclesLoadingComplete(vehicles: List<VehiclePresentation>) {

        EspressoIdlingResource.increment()
        _vehicleViewState.value =
            _vehicleViewState.value?.copy(isLoading = false, vehicles = vehicles)
    }

    fun cleanVehicles() {

        _vehicleViewState.value =
            ListVehiclesViewState(isLoading = true, error = null, vehicles = null)
    }

    // endregion
}
