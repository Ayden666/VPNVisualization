package com.example.vpnvisualization.data

import com.example.vpnvisualization.data.local.LocalConnectionsDataProvider
import com.example.vpnvisualization.model.ConnectionItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConnectionsRepositoryImpl: ConnectionsRepository {
    override fun getAllConnections(): Flow<MutableList<ConnectionItem>> = flow {
        emit(LocalConnectionsDataProvider.connectionItems)
    }

    override fun getAllStarredDestinations(): Flow<MutableSet<String>> = flow {
        emit(LocalConnectionsDataProvider.starredDestinations)
    }
}