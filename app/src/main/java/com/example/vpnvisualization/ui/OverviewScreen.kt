package com.example.vpnvisualization.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vpnvisualization.R
import com.example.vpnvisualization.VisualizationScreen
import com.example.vpnvisualization.model.ConnectionItem

val CONNECTION_WIDTH_BASE = 16.dp

@Composable
fun OverviewScreen (
    vpnUiState: VpnUiState,
    viewModel: VpnViewModel,
    onAppNodeClicked: (ConnectionItem) -> Unit,
    onConnectionClicked: (ConnectionItem) -> Unit,
    onDismissRequest: () -> Unit,
    onConnectionEdit: (ConnectionItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var showConnectionDetail = remember { mutableStateOf(false) }

    if (showConnectionDetail.value) {
        ConnectionDetailDialog(
            uiState = vpnUiState,
            onDismissRequest = {
                onDismissRequest()
                showConnectionDetail.value = false
            },
            onEdit = onConnectionEdit
        )
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxSize()
    ) {
        vpnUiState.connections.forEach { item ->
            ConnectionOverview(
                viewModel = viewModel,
                connectionItem = item,
                onAppNodeClicked = onAppNodeClicked,
                onConnectionClicked = { connItem ->
                    onConnectionClicked(connItem)
                    showConnectionDetail.value = true
                }
            )
        }
    }
}

@Composable
fun ConnectionOverview(
    viewModel: VpnViewModel,
    connectionItem: ConnectionItem,
    onAppNodeClicked: (ConnectionItem) -> Unit,
    onConnectionClicked: (ConnectionItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val destNodeHighlight = viewModel.checkContainStarredDest(connectionItem.destinationNode)
        Button(
            onClick = { /*TODO*/ },
            colors = if (destNodeHighlight) ButtonDefaults.buttonColors(containerColor = Color.Red) else ButtonDefaults.buttonColors()
        ) {
            val destinationLogo = if (connectionItem.connection.isVPN) R.drawable.baseline_vpn_lock_24 else R.drawable.baseline_public_24
            Icon(
                painter = painterResource(id = destinationLogo),
                contentDescription = stringResource(id = R.string.overview_destination_node),
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            val connectionLogo = if (connectionItem.connection.isVPN) R.drawable.baseline_lock_24 else R.drawable.baseline_lock_open_24
            Button(
                onClick = {
                    if (connectionItem.connection.isVPN) {
                        onConnectionClicked(connectionItem)
                    }
                },
                modifier = Modifier
                    .width(CONNECTION_WIDTH_BASE * connectionItem.appNode.flowWeight)
                    .fillMaxHeight()
            ) {}
            Icon(
                painter = painterResource(id = connectionLogo),
                contentDescription = null
            )

        }

        Button(
            onClick = { onAppNodeClicked(connectionItem) }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = connectionItem.appNode.logo), 
                    contentDescription = stringResource(id = connectionItem.appNode.name),
                    modifier = Modifier.size(24.dp)
                )
                Text(text = stringResource(id = connectionItem.appNode.name))
            }
        }
    }
}

