package com.csosa.fntest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.csosa.fntest.commons.ExceptionHandler
import com.csosa.fntest.domain.model.Bounds
import com.csosa.fntest.domain.model.Coordinate
import com.csosa.fntest.domain.usecases.GetAllVehiclesBaseUseCase
import com.csosa.fntest.domain.usecases.GetAllVehiclesUseCaseInput
import com.csosa.fntest.idlingresource.EspressoIdlingResource
import com.csosa.fntest.mappers.toPresentation
import com.csosa.fntest.models.VehiclePresentation
import com.csosa.fntest.models.states.Error
import com.csosa.fntest.models.states.MapsViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

internal class MapsViewModel(
    private val getAllVehiclesUseCase: GetAllVehiclesBaseUseCase
) : BaseViewModel() {

    // region Members

    private var getAllVehicleJob: Job? = null

    val mapsViewState: LiveData<MapsViewState>
        get() = _mapsViewState

    private var _mapsViewState = MutableLiveData<MapsViewState>()

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        val message = ExceptionHandler.parse(exception)
        _mapsViewState.value =
                _mapsViewState.value?.copy(isLoading = false, error = Error(message))
    }

    // endregion

    // region Constructors

    init {
        _mapsViewState.value =
            MapsViewState(isLoading = true, error = null, vehicles = null, selected = null)

    }

    // endregion

    // region Android API

    override fun onCleared() {
        super.onCleared()
        getAllVehicleJob?.cancel()
    }

    // endregion

    // region Public API

    fun executeGetVehiclesByBounds(apiKey:String, bounds: Bounds, location: Coordinate) {
        EspressoIdlingResource.increment()

        getAllVehicleJob?.cancel()
        getAllVehicleJob = launchCoroutine {
            delay(500)
            onVehiclesLoading()
            getAllVehiclesUseCase(GetAllVehiclesUseCaseInput(bounds, location, apiKey)).collect { vehicles ->

                val vehiclesPresentation = vehicles.map { it.toPresentation() }
                onVehiclesLoadingComplete(vehiclesPresentation)
            }
        }
    }

    // endregion

    // region Private API

    private fun onVehiclesLoading() {

        EspressoIdlingResource.increment()
        _mapsViewState.value = _mapsViewState.value?.copy(isLoading = true)
    }

    private fun onVehiclesLoadingComplete(vehicles: List<VehiclePresentation>) {

        EspressoIdlingResource.increment()
        _mapsViewState.value =
            _mapsViewState.value?.copy(isLoading = false, vehicles = vehicles)
    }

    fun updateSelected(vehiclePresentation: VehiclePresentation?) {

        EspressoIdlingResource.increment()
        _mapsViewState.value =
                _mapsViewState.value?.copy(isLoading = false, selected = vehiclePresentation)

    }

    fun displayVehicleError(message: Int) {

        EspressoIdlingResource.increment()
        _mapsViewState.value = _mapsViewState.value?.copy(error = Error(message))

    }

    // endregion
}
