package com.csosa.fntest.models

import android.os.Parcelable
import com.csosa.fntest.domain.model.Coordinate
import kotlinx.android.parcel.Parcelize

@Parcelize
internal data class CoordinatePresentation(
                                     val latitude: Double,
                                     val longitude: Double) : Parcelable
