package com.example.vpnvisualization.data

import com.example.vpnvisualization.model.ConnectionItem
import kotlinx.coroutines.flow.Flow

interface ConnectionsRepository {
    fun getAllConnections(): Flow<MutableList<ConnectionItem>>
    fun getAllStarredDestinations(): Flow<MutableSet<String>>
}