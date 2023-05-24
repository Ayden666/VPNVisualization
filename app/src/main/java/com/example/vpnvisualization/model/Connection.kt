package com.example.vpnvisualization.model

data class Connection(
    val isVPN: Boolean = false,
    val nickname: String = "",
    val ip: String = "",
    val location: String = "",
)
