package com.csosa.fntest.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.csosa.fntest.domain.model.FleetType
import kotlinx.android.parcel.Parcelize
import java.time.Duration

@Parcelize
internal data class VehiclePresentation(
    val id: Long,
    val fleetType: String,
    val coordinate: CoordinatePresentation,
    val heading: Float,
    val duration: String,
    val distance: String,
    @DrawableRes
    val imageRes: Int
) : Parcelable
