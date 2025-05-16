package org.chrontax.musicstreaming.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.chrontax.musicstreaming.data.User
import org.chrontax.musicstreaming.navigation.AppDestinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = hiltViewModel()) {
    val errorState by loginViewModel.errorState.collectAsState()
    val loggedIn by loginViewModel.loggedIn.collectAsState()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    if (loggedIn) {
        navController.navigate(AppDestinations.SEARCH_ROUTE) {
            popUpTo(AppDestinations.LOGIN_ROUTE) { inclusive = true }
        }
    }

    Scaffold(topBar = { TopAppBar(title = {Text("Login")}) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Login")

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                loginViewModel.login(User(username, password))
            }) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                navController.navigate(AppDestinations.REGISTER_ROUTE)
            }) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                navController.navigate(AppDestinations.SETTINGS_ROUTE)
            }) {
                Text("Settings")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (errorState != null) {
                Text(errorState!!)
            }
        }
    }
}