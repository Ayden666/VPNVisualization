package com.example.vpnvisualization.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class AppNode(
    @StringRes val name: Int = -1,
    @DrawableRes val logo: Int = -1,
    val flowWeight: Float = -1f
)