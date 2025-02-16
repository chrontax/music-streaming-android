package org.chrontax.musicstreaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.chrontax.musicstreaming.data.SettingsManager
import org.chrontax.musicstreaming.navigation.AppDestinations
import org.chrontax.musicstreaming.navigation.AppNavHost
import org.chrontax.musicstreaming.ui.theme.MusicStreamingTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicStreamingTheme {
                val navController = rememberNavController()
                lifecycleScope.launch {
                    settingsManager.tokenFlow.collect {
                        if (it == null)
                            navController.navigate(AppDestinations.LOGIN_ROUTE)
                    }
                }
                AppNavHost(navController = navController)
            }
        }
    }
}