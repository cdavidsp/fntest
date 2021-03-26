package com.csosa.fntest.adapters

import android.view.View
import com.csosa.fntest.databinding.ItemVehicleBinding
import com.csosa.fntest.models.VehiclePresentation
import me.ibrahimyilmaz.kiel.core.RecyclerViewHolder

internal class VehicleViewHolder(
    view: View
) : RecyclerViewHolder<VehiclePresentation>(view) {

    val binding = ItemVehicleBinding.bind(view)
    override fun bind(position: Int, item: VehiclePresentation) {

        super.bind(position, item)
        binding.executePendingBindings()
    }
}
