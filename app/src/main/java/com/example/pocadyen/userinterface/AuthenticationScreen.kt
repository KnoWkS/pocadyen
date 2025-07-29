package com.example.pocadyen.userinterface

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pocadyen.viewmodel.AuthenticationState
import com.example.pocadyen.viewmodel.AuthenticationViewModel

@Composable
fun AuthenticationScreen(viewModel: AuthenticationViewModel, onTokenGenerated: (String) -> Unit) {

    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        when (state) {
            is AuthenticationState.Idle -> {
                Button(onClick = { viewModel.authenticateMerchant() }) {
                    Text("Démarrer authentification")
                }
            }

            is AuthenticationState.Loading -> {
                CircularProgressIndicator()
            }

            is AuthenticationState.Success -> {
                val setupToken = (state as AuthenticationState.Success).setupToken
                LaunchedEffect(Unit) {
                    onTokenGenerated(setupToken)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("✅ Setup Token obtenu !", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(setupToken, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.resetState() }) {
                        Text("Réinitialiser")
                    }
                }
            }

            is AuthenticationState.Error -> {
                val message = (state as AuthenticationState.Error).message
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("❌ Erreur :", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(message)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.resetState() }) {
                        Text("Réessayer")
                    }
                }
            }
        }
    }
}
