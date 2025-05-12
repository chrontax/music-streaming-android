package org.chrontax.musicstreaming.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.chrontax.musicstreaming.ui.LoginScreen
import org.chrontax.musicstreaming.ui.ReleaseScreen
import org.chrontax.musicstreaming.ui.SearchScreen
import org.chrontax.musicstreaming.ui.SettingsScreen
import kotlin.uuid.Uuid

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
        composable(AppDestinations.SEARCH_ROUTE) {
            SearchScreen(navController = navController)
        }
        composable(
            route = AppDestinations.RELEASE_ROUTE,
            arguments = listOf(navArgument(AppDestinations.RELEASE_ID_ARG) {
                type = NavType.StringType
            })
        ) {
            val releaseId = it.arguments?.getString(AppDestinations.RELEASE_ID_ARG)!!

            ReleaseScreen(releaseId = Uuid.parse(releaseId))
        }
    }
}