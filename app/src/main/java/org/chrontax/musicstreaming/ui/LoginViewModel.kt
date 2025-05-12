package org.chrontax.musicstreaming.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.chrontax.musicstreaming.data.SettingsManager
import org.chrontax.musicstreaming.data.User
import org.chrontax.musicstreaming.data.UserApiResult
import org.chrontax.musicstreaming.data.UserRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val settingsManager: SettingsManager,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState
    private val _loggedIn = MutableStateFlow(false)
    val loggedIn: StateFlow<Boolean> = _loggedIn

    init {
        viewModelScope.launch {
            _loggedIn.value = settingsManager.tokenFlow.first() != null
        }
    }

    fun login(user: User) {
        viewModelScope.launch {
            when (val result = userRepository.login(user)) {
                is UserApiResult.Success -> {
                    settingsManager.setToken(result.token)
                    _loggedIn.value = true
                }

                is UserApiResult.Failure -> {
                    _errorState.value = result.message
                }
            }
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            val result = userRepository.register(user)
            when (result) {
                is UserApiResult.Success -> {
                    settingsManager.setToken(result.token)
                }

                is UserApiResult.Failure -> {
                    _errorState.value = result.message ?: "An error occurred"
                }
            }
        }
    }
}