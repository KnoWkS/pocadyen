package com.example.pocadyen.service

import com.adyen.ipp.authentication.AuthenticationProvider
import com.adyen.ipp.authentication.AuthenticationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class AuthenticationProviderImpl : AuthenticationProvider() {

    override suspend fun authenticate(setupToken: String): Result<AuthenticationResponse> {
        return withContext(Dispatchers.IO) {
            try {
                // Remplace par ton endpoint de test (backend ou mock)
                val url = "https://your-backend.com/checkout/possdk/v68/sessions?setupToken=$setupToken"

                val request = Request.Builder()
                    .url(url)
                    .get()
                    .build()

                val client = OkHttpClient()
                val response = client.newCall(request).execute()

                if (!response.isSuccessful) {
                    return@withContext Result.failure(Exception("Session request failed: ${response.code}"))
                }

                val body = response.body?.string() ?: return@withContext Result.failure(Exception("Empty response body"))

                val json = JSONObject(body)
                val sdkData = json.getString("sdkData")

                Result.success(AuthenticationResponse(sdkData))

            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
