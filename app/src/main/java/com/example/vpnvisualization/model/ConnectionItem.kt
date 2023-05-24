package com.example.vpnvisualization.model

import androidx.annotation.DrawableRes

data class ConnectionItem(
    val appNode: AppNode = AppNode(),
    val connection: Connection = Connection(),
    val destinationNode: DestinationNode = DestinationNode(),
    val id: Int = -1
)