package com.example.vpnvisualization.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vpnvisualization.R

@Composable
fun SettingsScreen(
    onConnectionListClick: () -> Unit,
    onDestinationListClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SettingsItem(
            text = R.string.manage_connections,
            onClick = onConnectionListClick
        )
        VpnDivider()
        SettingsItem(
            text = R.string.manage_starred_urls,
            onClick = onDestinationListClick
        )
    }
}

@Composable
fun SettingsItem(
    @StringRes text: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(text = stringResource(id = text))
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(
                id = R.drawable.baseline_arrow_forward_ios_24),
            contentDescription = null
        )
    }
}