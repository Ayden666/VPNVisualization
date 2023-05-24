package com.example.vpnvisualization.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vpnvisualization.R

@Composable
fun DestListScreen(
    viewModel: VpnViewModel,
    destList: MutableSet<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(destList.toList()) {
            DestListItem(
                dest = it
            )
        }
    }
}

@Composable
fun DestListItem(
    dest: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                DestInformation(dest = dest)
                Spacer(modifier = Modifier.weight(1f))
                DestEditButton(
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun DestInformation(
    dest: String,
    modifier: Modifier = Modifier
) {
    Text(dest)
}

@Composable
fun DestEditButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_edit_24),
            contentDescription = stringResource(id = R.string.edit))
    }
}