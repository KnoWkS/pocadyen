package com.example.pocadyen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocadyen.data.authentication.AuthenticationProvider
import com.example.pocadyen.data.authentication.SetupTokenRequest
import com.example.pocadyen.data.authentication.SetupTokenResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthenticationState {
    object Idle : AuthenticationState()
    object Loading : AuthenticationState()
    data class Success(val setupToken: String) : AuthenticationState()
    data class Error(val message: String) : AuthenticationState()
}

class AuthenticationViewModel(
    private val authProvider: AuthenticationProvider = AuthenticationProvider()
) : ViewModel() {

    private val _state = MutableStateFlow<AuthenticationState>(AuthenticationState.Idle)
    val state: StateFlow<AuthenticationState> = _state

    fun authenticateMerchant() {
        _state.value = AuthenticationState.Loading

        viewModelScope.launch {
            val request = SetupTokenRequest(
                amount = 1000,
                currency = "EUR",
                countryCode = "FR",
                shopperReference = "user-1234",
                reference = "order-9876"
            )

            authProvider.createSetupToken(request)
                .onSuccess { response ->
                    _state.value = AuthenticationState.Success(response.setupToken)
                }
                .onFailure { throwable ->
                    _state.value = AuthenticationState.Error(throwable.message ?: "Unknown error")
                }
        }
    }

    fun resetState() {
        _state.value = AuthenticationState.Idle
    }
}
