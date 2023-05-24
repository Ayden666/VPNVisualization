package com.example.vpnvisualization.model

import androidx.annotation.DrawableRes
import com.example.vpnvisualization.R

enum class FlowDirection(@DrawableRes val icon: Int) {
    In(icon = R.drawable.baseline_east_24),
    Out(icon = R.drawable.baseline_west_24),
    Both(icon = R.drawable.baseline_swap_horiz_24)
}

data class Destination(
    val domain: String = "",
    val ip: String = "",
    val direction: FlowDirection = FlowDirection.Both
)