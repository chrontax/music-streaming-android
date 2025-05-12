package org.chrontax.musicstreaming.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlin.uuid.Uuid

@Composable
fun ReleaseCoverImage(
    releaseId: Uuid,
    viewModel: ReleaseCoverImageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(releaseId) {
        viewModel.loadImage(releaseId)
    }

    Box(
        modifier = Modifier.size(128.dp), // Apply a fixed size to the container
        contentAlignment = Alignment.Center // Center content within the box
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.size(48.dp))
            }

            uiState.error != null -> {
                Text("Error loading image")
            }

            uiState.imageBitmap != null -> {
                Image(
                    bitmap = uiState.imageBitmap!!.asImageBitmap(),
                    contentDescription = "Cover image for release $releaseId",
                    modifier = Modifier.size(128.dp) // Image fills the Box
                )
            }

            else -> {
                // Placeholder
            }
        }
    }
}