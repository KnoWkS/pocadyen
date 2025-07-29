package com.example.pocadyen.userinterface

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pocadyen.viewmodel.SessionViewModel
import com.example.pocadyen.model.SessionUiState

@Composable
fun SessionScreen(viewModel: SessionViewModel, setupToken: String) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.createSession(setupToken)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Initier une session Tap to Pay", style = MaterialTheme.typography.titleLarge)


        when (state) {
            is SessionUiState.Idle -> {}
            is SessionUiState.Loading -> {
                CircularProgressIndicator()
            }
            is SessionUiState.Success -> {
                val session = (state as SessionUiState.Success).session
                Text("Session créée ! ID: ${session.id}")
                Text("Installation ID: ${session.installationId}")
                Text("SDK Data: ${session.sdkData.take(30)}...")
            }
            is SessionUiState.Error -> {
                Text("Erreur : ${(state as SessionUiState.Error).message}", color = MaterialTheme.colorScheme.error)
            }
            else -> {
                Text("État inconnu")
            }
        }
    }
}
