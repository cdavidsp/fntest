package com.csosa.fntest.commons

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.csosa.fntest.domain.model.Bounds
import com.csosa.fntest.domain.model.Coordinate
import com.google.android.gms.maps.model.LatLngBounds


internal fun View.show() {
    this.visibility = View.VISIBLE
}

internal fun View.hide() {
    this.visibility = View.GONE
}

internal fun LatLngBounds.toBounds(): Bounds {
    return Bounds(
            Coordinate(this.northeast.latitude, this.southwest.longitude),
            Coordinate(this.southwest.latitude, this.northeast.longitude)

    )
}

@BindingAdapter("android:src")
fun setImageViewResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}
