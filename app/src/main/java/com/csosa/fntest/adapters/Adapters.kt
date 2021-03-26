package com.csosa.fntest.adapters

import com.csosa.fntest.R
import com.csosa.fntest.models.VehiclePresentation
import me.ibrahimyilmaz.kiel.adapterOf

@Suppress("NOTHING_TO_INLINE")
internal inline fun createVehiclesAdapter(noinline onClick: (VehiclePresentation) -> Unit) =
    adapterOf<VehiclePresentation> {
        diff(
            areItemsTheSame = { old, new -> old === new },
            areContentsTheSame = { old, new ->
                old.id == new.id
            }
        )
        register(
            layoutResource = R.layout.item_vehicle,
            viewHolder = ::VehicleViewHolder,
            onBindViewHolder = { vh, _, vehiclePresentation ->
                vh.binding.vehicle = vehiclePresentation
                vh.binding.layoutVehicleItem.setOnClickListener {
                    onClick(vehiclePresentation)
                }
            }
        )
    }
