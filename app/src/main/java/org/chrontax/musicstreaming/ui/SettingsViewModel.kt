package org.chrontax.musicstreaming.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.chrontax.musicstreaming.data.SettingsManager
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsManager: SettingsManager) :
    ViewModel() {
    val baseUrl = settingsManager.baseUrlFlow

    fun setBaseUrl(url: String) {
        viewModelScope.launch {
            settingsManager.setBaseUrl(url)
        }
    }
}