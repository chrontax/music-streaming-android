package org.chrontax.musicstreaming.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.chrontax.musicstreaming.network.MusicApi
import javax.inject.Inject
import kotlin.uuid.Uuid

data class ReleaseImageUiState(
    val imageBitmap: Bitmap? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)

@HiltViewModel
class ReleaseCoverImageViewModel @Inject constructor(
    private val musicApi: MusicApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReleaseImageUiState())
    val uiState: StateFlow<ReleaseImageUiState> = _uiState

    fun loadImage(releaseId: Uuid) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val responseBody = withContext(Dispatchers.IO) {
                    musicApi.getReleaseCover(releaseId)
                }
                val bitmap = withContext(Dispatchers.IO) {
                    BitmapFactory.decodeStream(responseBody.byteStream())
                }
                _uiState.value = _uiState.value.copy(imageBitmap = bitmap)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e)
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}