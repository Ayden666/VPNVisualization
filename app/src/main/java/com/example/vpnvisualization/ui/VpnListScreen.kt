package com.example.vpnvisualization.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vpnvisualization.R
import com.example.vpnvisualization.model.ConnectionItem

@Composable
fun VpnListScreen(
    uiState: VpnUiState,
    onEdit: (ConnectionItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(uiState.connections) {
            if (it.connection.isVPN) {
                VpnListItem(
                    connectionItem = it,
                    onEdit = onEdit
                )
            }
        }
    }
}

@Composable
fun VpnListItem(
    connectionItem: ConnectionItem,
    onEdit: (ConnectionItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                VpnInformation(connectionItem = connectionItem)
                Spacer(modifier = Modifier.weight(1f))
                VpnEditButton(
                    connectionItem = connectionItem,
                    onClick = onEdit
                )
            }
        }
    }
}

@Composable
fun VpnInformation(
    connectionItem: ConnectionItem,
    modifier: Modifier = Modifier
) {
    Column {
        Text(text = stringResource(id = R.string.nickname) + connectionItem.connection.nickname)
        Text(text = stringResource(id = R.string.ip_address) + connectionItem.connection.ip)
        Text(text = stringResource(id = R.string.location) + connectionItem.connection.location)
    }
}

@Composable
fun VpnEditButton(
    connectionItem: ConnectionItem,
    onClick: (ConnectionItem) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = { onClick(connectionItem) }) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_edit_24),
            contentDescription = stringResource(id = R.string.edit)
        )
    }
}