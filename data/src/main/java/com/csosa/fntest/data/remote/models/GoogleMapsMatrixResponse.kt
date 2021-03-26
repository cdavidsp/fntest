package com.csosa.fntest.data.remote.models

data class GoogleMapsMatrixResponse(
        val status: String,
    val rows: List<GoogleMapsMatrixRowResponse>
)

data class GoogleMapsMatrixRowResponse(
    val elements: List<GoogleMapsMatrixElementResponse>
)
data class GoogleMapsMatrixElementResponse(
    val status: String,
    val duration: GoogleMapsMatrixValueResponse,
    val distance: GoogleMapsMatrixValueResponse

)
data class GoogleMapsMatrixValueResponse(
        val value: Double,
        val text: String
)

