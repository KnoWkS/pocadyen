package com.example.pocadyen.model

import com.example.pocadyen.data.SessionResponse

sealed class SessionUiState {
    object Idle : SessionUiState()
    object Loading : SessionUiState()
    data class Success(val session: SessionResponse) : SessionUiState()
    data class Error(val message: String) : SessionUiState()
}
