package com.example.vpnvisualization.ui

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vpnvisualization.R
import com.example.vpnvisualization.TAG
import com.example.vpnvisualization.data.local.LocalConnectionsDataProvider.connectionItems
import com.example.vpnvisualization.model.AppNode
import com.example.vpnvisualization.model.Connection
import com.example.vpnvisualization.model.ConnectionItem
import com.example.vpnvisualization.model.Destination
import com.example.vpnvisualization.model.DestinationNode


@Composable
fun AppDetailScreen(
    viewModel: VpnViewModel,
    uiState: VpnUiState,
    onEditConnection: (ConnectionItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight(0.33f)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            DestinationNode(
                viewModel = viewModel,
                destinationNode = uiState.selectedApp.destinationNode,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxHeight(0.33f)
                .padding(16.dp)
        ) {
            ConnectionNode(
                connectionItem = uiState.selectedApp,
                onEditConnection = onEditConnection,
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Row(
            modifier = Modifier
                .fillMaxHeight(0.33f)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            AppNode(
                appNode = uiState.selectedApp.appNode,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
fun AppNode(
    appNode: AppNode,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = appNode.logo),
                contentDescription = stringResource(id = appNode.name),
                modifier = Modifier.size(42.dp)
            )
            Text(text = stringResource(id = appNode.name))
        }
    }
}

@Composable
fun ConnectionNode(
    connectionItem: ConnectionItem,
    onEditConnection: (ConnectionItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var connection by remember {
        mutableStateOf(connectionItem.connection)
    }

    connection = connectionItem.connection

    Card(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_dns_24),
                contentDescription = null,
                modifier = Modifier.size(42.dp)
            )
            if (connection.isVPN) {
                Text(text = connection.nickname)
                Text(text = connection.ip)
                IconButton(
                    onClick = {
                        onEditConnection(connectionItem)
                        connection = connectionItem.connection
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_edit_24),
                        contentDescription = stringResource(id = R.string.edit)
                    )
                }
            }
        }
    }
}

@Composable
fun DestinationNode(
    viewModel: VpnViewModel,
    destinationNode: DestinationNode,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(destinationNode.destinations) {
                DestinationItem(
                    viewModel = viewModel,
                    destination = it
                )
            }
        }
    }
}

@Composable
fun DestinationItem(
    viewModel: VpnViewModel,
    destination: Destination,
    modifier: Modifier = Modifier
) {
    var isStarred by remember { mutableStateOf(viewModel.checkDestStarred(destination)) }
    Log.d(TAG, "checkDestStarred() called with " + destination.domain + "Result: " + isStarred)
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = destination.direction.icon),
            contentDescription = null,
        )
        Text(text = destination.domain)
        Spacer(modifier = Modifier.weight(1f))
        StarButton(
            isStarred = isStarred,
            onClick = {
                if (isStarred) {
                    viewModel.unstarDest(destination.domain)
                } else {
                    viewModel.starDest(destination.domain)
                }
                isStarred = viewModel.checkDestStarred(destination)
            }
        )
    }
}

@Composable
fun StarButton(
    isStarred: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconToggleButton(
        checked = isStarred,
        onCheckedChange = { onClick() }
    ) {
        @DrawableRes val painterId = if (isStarred) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
        @StringRes val descriptionId = if (isStarred) R.string.unstar else R.string.star
        Icon(
            painter = painterResource(id = painterId),
            contentDescription = stringResource(id = descriptionId))
    }
}