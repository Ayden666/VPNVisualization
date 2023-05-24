package com.example.vpnvisualization

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vpnvisualization.ui.AppDetailScreen
import com.example.vpnvisualization.ui.DestListScreen
import com.example.vpnvisualization.ui.EditVpnScreen
import com.example.vpnvisualization.ui.OverviewScreen
import com.example.vpnvisualization.ui.SettingsScreen
import com.example.vpnvisualization.ui.VpnListScreen
import com.example.vpnvisualization.ui.VpnViewModel

enum class VisualizationScreen(
    @StringRes val title: Int,
    @DrawableRes val icon: Int = -1,
    val showBottomBar: Boolean = false
) {
    Overview(
        title = R.string.overview_screen_title,
        icon = R.drawable.baseline_cloud_24,
        showBottomBar = true
    ),
    Detail(title = R.string.detail_screen_title),
    Settings(
        title = R.string.settings_screen_title,
        icon = R.drawable.baseline_settings_24,
        showBottomBar = true
    ),
    VpnList(
        title = R.string.vpn_server_list,
        icon = R.drawable.baseline_format_list_bulleted_24,
    ),
    VpnEdit(title = R.string.edit_server_detail),
    DestList(title = R.string.starred_url)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VpnTopAppBar(
    currentScreen: VisualizationScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun VpnBottomAppBar(
    navController: NavHostController
) {
    val screens = VisualizationScreen.values().filter {
        it -> it.showBottomBar
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach {screen ->
            AddBottomBarItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddBottomBarItem(
    screen: VisualizationScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        label = {
            Text(text = stringResource(id = screen.title))
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = stringResource(id = screen.title)
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.name
        } == true,
        onClick = {
            navController.navigate(screen.name) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VPNVisualizationApp(modifier: Modifier = Modifier) {

    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = VisualizationScreen.valueOf(
        backStackEntry?.destination?.route ?: VisualizationScreen.Overview.name
    )

    val viewModel: VpnViewModel = viewModel()

    Scaffold(
        topBar = {
            VpnTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            if (currentScreen.showBottomBar) {
                VpnBottomAppBar(navController = navController)
            }
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = VisualizationScreen.Overview.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = VisualizationScreen.Overview.name) {
                OverviewScreen(
                    vpnUiState = uiState,
                    viewModel = viewModel,
                    onAppNodeClicked = { connItem ->
                        viewModel.setSelectedApp(connItem)
                        navController.navigate(VisualizationScreen.Detail.name)
                    },
                    onConnectionClicked = { connItem ->
                        viewModel.setSelectedConnection(connItem)
                    },
                    onDismissRequest = {
                        viewModel.clearSelectedConnection()
                    },
                    onConnectionEdit = { connItem ->
                        viewModel.setEditedConnectionItem(connItem)
                        navController.navigate(VisualizationScreen.VpnEdit.name)
                    }
                )
            }
            composable(route = VisualizationScreen.Detail.name) {
                AppDetailScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onEditConnection = { connItem ->
                        viewModel.setEditedConnectionItem(connItem)
                        navController.navigate(VisualizationScreen.VpnEdit.name)
                    }
                )
            }
            composable(route = VisualizationScreen.Settings.name) {
                SettingsScreen(
                    onConnectionListClick = {
                        navController.navigate(VisualizationScreen.VpnList.name)
                    },
                    onDestinationListClick = {
                        navController.navigate(VisualizationScreen.DestList.name)
                    }
                )
            }
            composable(route = VisualizationScreen.VpnList.name) {
                VpnListScreen(
                    uiState = uiState,
                    onEdit = { connItem ->
                        viewModel.setEditedConnectionItem(connItem)
                        navController.navigate(VisualizationScreen.VpnEdit.name)
                    }
                )
            }
            composable(route = VisualizationScreen.VpnEdit.name) {
                EditVpnScreen(
                    viewModel = viewModel,
                    connectionItem = uiState.editedConnectionItem,
                    navController = navController
                )
            }
            composable(route = VisualizationScreen.DestList.name) {
                DestListScreen(
                    viewModel = viewModel,
                    destList = uiState.starredDestinations
                )
            }
        }

    }


}
