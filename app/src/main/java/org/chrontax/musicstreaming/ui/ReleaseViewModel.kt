package org.chrontax.musicstreaming.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.chrontax.musicstreaming.data.Release
import org.chrontax.musicstreaming.network.MusicApi
import javax.inject.Inject
import kotlin.uuid.Uuid

data class ReleaseUiState(
    val release: Release? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)

class ReleaseViewModel @Inject constructor(
    private val musicApi: MusicApi
) : ViewModel() {

    var uiState by mutableStateOf(ReleaseUiState())
        private set

    fun loadRelease(releaseId: Uuid) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                val release = musicApi.getRelease(releaseId)
                uiState = uiState.copy(release = release)
            } catch (e: Exception) {
                uiState = uiState.copy(error = e)
            } finally {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }
}