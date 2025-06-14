package org.chrontax.musicstreaming.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.net.MalformedURLException
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    var isUrlValid by remember { mutableStateOf(true) }
    var newBaseUrl by remember { mutableStateOf(runBlocking { settingsViewModel.baseUrl.first() }) }

    Scaffold(topBar = { TopAppBar(title = {Text("Login")}) }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = newBaseUrl,
                onValueChange = {
                    newBaseUrl = it
                    isUrlValid = isValidUrl(it)
                    if (isUrlValid)
                        settingsViewModel.setBaseUrl(it)
                },
                label = { Text("Server URL") },
                isError = !isUrlValid
            )
        }
    }
}

fun isValidUrl(url: String): Boolean {
    return try {
        val parsedUrl = URL(url)
        parsedUrl.protocol == "http" || parsedUrl.protocol == "https"
    } catch (e: MalformedURLException) {
        false
    }
}