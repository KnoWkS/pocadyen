package com.example.pocadyen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocadyen.data.SessionProvider
import com.example.pocadyen.model.SessionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SessionViewModel(
    private val sessionProvider: SessionProvider = SessionProvider()
) : ViewModel() {

    private val _state = MutableStateFlow<SessionUiState>(SessionUiState.Idle)
    val state: StateFlow<SessionUiState> = _state

    fun createSession(setupToken: String) {
        _state.value = SessionUiState.Loading

        viewModelScope.launch {
            val result = sessionProvider.createSession(setupToken)

            _state.value = result.fold(
                onSuccess = { SessionUiState.Success(it) },
                onFailure = { SessionUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun reset() {
        _state.value = SessionUiState.Idle
    }
}
