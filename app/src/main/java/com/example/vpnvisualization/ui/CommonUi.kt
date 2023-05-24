package com.example.vpnvisualization.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VpnDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = Color.Gray.copy(alpha = 0.08f)
    )
}