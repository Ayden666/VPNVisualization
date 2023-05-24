package com.example.vpnvisualization.ui

import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vpnvisualization.data.ConnectionsRepository
import com.example.vpnvisualization.data.ConnectionsRepositoryImpl
import com.example.vpnvisualization.model.ConnectionItem
import com.example.vpnvisualization.model.Destination
import com.example.vpnvisualization.model.DestinationNode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class VpnViewModel(
    private val connectionsRepository: ConnectionsRepository = ConnectionsRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(VpnUiState())
    val uiState: StateFlow<VpnUiState> = _uiState.asStateFlow()

    init {
        observeConnections()
    }

    private fun observeConnections() {
        viewModelScope.launch {
            connectionsRepository.getAllConnections()
                .catch { ex->
                    _uiState.value = VpnUiState(error = ex.message)
                }
                .collect { connections ->
                    _uiState.value = VpnUiState(
                        connections = connections
                    )
                }

            connectionsRepository.getAllStarredDestinations()
                .catch { ex ->
                    _uiState.value = VpnUiState(error = ex.message)
                }
                .collect { dests ->
                    _uiState.value = _uiState.value.copy(
                        starredDestinations = dests
                    )
                }
        }
    }

    fun setSelectedApp(connectionItem: ConnectionItem) {
        _uiState.value = _uiState.value.copy(
            selectedApp = connectionItem
        )
    }

    fun clearSelectedApp() {
        _uiState.value = _uiState.value.copy(
            selectedApp = ConnectionItem()
        )
    }

    fun setSelectedConnection(connectionItem: ConnectionItem) {
        _uiState.value = _uiState.value.copy(
            selectedConnection = connectionItem
        )
    }

    fun clearSelectedConnection() {
        _uiState.value = _uiState.value.copy(
            selectedConnection = ConnectionItem()
        )
    }

    fun setEditedConnectionItem(connectionItem: ConnectionItem) {
        _uiState.value = _uiState.value.copy(
            editedConnectionItem = connectionItem
        )
    }

    fun clearEditedConnectionItem() {
        _uiState.value = _uiState.value.copy(
            editedConnectionItem = ConnectionItem()
        )
    }

    fun modifyConnectionDetail(
        id: Int,
        newNickname: String = "",
        newIp: String = "",
        newLocation: String = ""
    ) {
        var connectionsList = _uiState.value.connections
        val oldConnectionPair = connectionsList
            .withIndex()
            .first { it.value.id == id }

        var newConnection = oldConnectionPair.value.connection.copy()

        newConnection = newConnection.copy(
            nickname = newNickname,
            ip = newIp,
            location = newLocation
        )

        val newConnectionItem = oldConnectionPair.value.copy(
            connection = newConnection
        )
        connectionsList[oldConnectionPair.index] = newConnectionItem
    }

    fun checkDestStarred(
        destination: Destination
    ): Boolean {
        return _uiState.value.starredDestinations.contains(destination.domain)
    }

    fun starDest(
        dest: String
    ) {
        _uiState.value.starredDestinations.add(dest)
    }

    fun unstarDest(
        dest: String
    ) {
        _uiState.value.starredDestinations.remove(dest)
    }

    fun checkContainStarredDest(
        destinationNode: DestinationNode
    ) : Boolean {
        destinationNode.destinations.forEach {
            if (_uiState.value.starredDestinations.contains(it.domain)) {
                return true
            }
        }
        return false
    }
}

data class VpnUiState(
    var connections: MutableList<ConnectionItem> = mutableListOf<ConnectionItem>(),
    var selectedApp: ConnectionItem = ConnectionItem(),
    var selectedConnection: ConnectionItem = ConnectionItem(),
    var editedConnectionItem: ConnectionItem = ConnectionItem(),
    var starredDestinations: MutableSet<String> = mutableSetOf<String>(),
    var error: String? = null,
    var nextId: Int = 3
)