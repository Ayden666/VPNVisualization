package com.example.vpnvisualization.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.vpnvisualization.R
import com.example.vpnvisualization.model.ConnectionItem

@Composable
fun EditVpnScreen(
    viewModel: VpnViewModel,
    connectionItem: ConnectionItem,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var nickname by remember { mutableStateOf(connectionItem.connection.nickname) }
    var ip by remember { mutableStateOf(connectionItem.connection.ip) }
    var location by remember { mutableStateOf(connectionItem.connection.location) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        VpnTextField(
            label = R.string.nickname,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            value = nickname,
            onValueChange = { nickname = it}
        )
        VpnTextField(
            label = R.string.ip_address,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            value = ip,
            onValueChange = { ip = it}
        )
        VpnTextField(
            label = R.string.location,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            value = location,
            onValueChange = { location = it}
        )
        Button(onClick = {
            viewModel.modifyConnectionDetail(
                id = connectionItem.id,
                newNickname = nickname,
                newIp = ip,
                newLocation = location
            )
            navController.navigateUp()
        }) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VpnTextField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        label = {
            Text(stringResource(id = label))
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}