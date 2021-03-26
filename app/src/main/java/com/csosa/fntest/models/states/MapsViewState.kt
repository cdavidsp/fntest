package com.csosa.fntest.models.states

import com.csosa.fntest.models.VehiclePresentation

internal data class MapsViewState(
    val isLoading: Boolean,
    val error: Error?,
    val vehicles: List<VehiclePresentation>?,
    val selected: VehiclePresentation?
)
