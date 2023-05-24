package com.example.vpnvisualization.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.vpnvisualization.R
import com.example.vpnvisualization.model.ConnectionItem

@Composable
fun ConnectionDetailDialog(
    uiState: VpnUiState,
    onDismissRequest: () -> Unit,
    onEdit: (ConnectionItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box() {
            Card(
                elevation = CardDefaults.elevatedCardElevation(),
                modifier = modifier
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(
                        onClick = onDismissRequest
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = stringResource(id = R.string.close)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { onEdit(uiState.selectedConnection) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_edit_24),
                            contentDescription = stringResource(id = R.string.edit)
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    ConnectionDetailItem(
                        title = R.string.nickname,
                        value = uiState.selectedConnection.connection.nickname,
                    )
                    Divider(
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    ConnectionDetailItem(
                        title = R.string.ip_address,
                        value = uiState.selectedConnection.connection.ip,
                    )
                    Divider(
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    ConnectionDetailItem(
                        title = R.string.location,
                        value = uiState.selectedConnection.connection.location,
                    )
                }
            }
        }
    }
}

@Composable
fun ConnectionDetailItem(
    title: Int,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(stringResource(id = title) + ": ")
        Text(value)
    }
}

