package com.example.pocadyen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import com.example.pocadyen.userinterface.AuthenticationScreen
import com.example.pocadyen.userinterface.SessionScreen
import com.example.pocadyen.viewmodel.AuthenticationViewModel
import com.example.pocadyen.viewmodel.SessionViewModel
import com.example.pocadyen.ui.theme.POCAdyenTheme // adapte si besoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authViewModel = AuthenticationViewModel()
        val sessionViewModel = SessionViewModel()

        setContent {
            POCAdyenTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    // Mini navigation logique entre les deux écrans
                    var setupToken by remember { mutableStateOf<String?>(null) }

                    if (setupToken == null) {
                        AuthenticationScreen(
                            viewModel = authViewModel,
                            onTokenGenerated = { token ->
                                setupToken = token
                            }
                        )
                    } else {
                        SessionScreen(
                            viewModel = sessionViewModel,
                            setupToken = setupToken!!
                        )
                    }
                }
            }
        }
    }
}
