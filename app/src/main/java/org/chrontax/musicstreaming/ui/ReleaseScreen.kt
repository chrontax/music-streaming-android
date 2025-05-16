package org.chrontax.musicstreaming.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.chrontax.musicstreaming.data.Release
import kotlin.uuid.Uuid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReleaseScreen(
    releaseId: Uuid, // Accept releaseId as a Uuid
    viewModel: ReleaseViewModel = hiltViewModel() // Get the ViewModel
) {
    val uiState = viewModel.uiState

    // Trigger data loading when the screen is first composed or releaseId changes
    LaunchedEffect(releaseId) {
        viewModel.loadRelease(releaseId)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Release Details") }) // You might want to display the release name here once loaded
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.error != null -> {
                    Text("Error loading release: ${uiState.error.localizedMessage}")
                }

                uiState.release != null -> {
                    ReleaseDetails(release = uiState.release) // Display the loaded release details
                }

                else -> {
                    // Optional: Show a placeholder if no release is loaded and not loading/error
                    Text("Release not found")
                }
            }
        }
    }
}

@Composable
fun ReleaseDetails(release: Release) { // Use the correct Release data class
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ReleaseCoverImage(release.id)
        Text("Name: ${release.name}", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Original Name: ${release.original_name}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Type: ${release.type.name}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        // You would typically display artists and tracks here as well
        // For brevity, we'll just show basic info
        Text(
            "Number of Tracks: ${release.tracks.size}",
            style = MaterialTheme.typography.bodyMedium
        )
        // You can add more details like artist names, track list, cover art, etc.
        // Example: Call the ReleaseCoverImage composable you created earlier
        // ReleaseCoverImageFromRetrofit(releaseId = release.id, musicApi = /* provide musicApi */)
    }
}