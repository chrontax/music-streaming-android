package org.chrontax.musicstreaming.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.chrontax.musicstreaming.ui.LoginScreen
import org.chrontax.musicstreaming.ui.SettingsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestinations.LOGIN_ROUTE
) {
    val currentNavBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry.value?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        composable(AppDestinations.LOGIN_ROUTE) {
            LoginScreen(navController = navController)
        }
        composable(AppDestinations.SETTINGS_ROUTE) {
            SettingsScreen()
        }
    }
}