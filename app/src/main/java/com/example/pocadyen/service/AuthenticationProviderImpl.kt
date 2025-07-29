package com.example.pocadyen.data.authentication

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

@Serializable
data class SetupTokenRequest(
    val amount: Int,
    val currency: String,
    val countryCode: String,
    val shopperReference: String,
    val reference: String
)

@Serializable
data class SetupTokenResponse(
    val setupToken: String
)

class AuthenticationProvider(
    private val client: OkHttpClient = OkHttpClient(),
    private val baseUrl: String = "http://10.0.2.2:8080" // Android emulator talks to localhost via 10.0.2.2
) {
    suspend fun createSetupToken(request: SetupTokenRequest): Result<SetupTokenResponse> {
        return try {
            val jsonBody = Json.encodeToJsonElement(request).toString()

            val httpRequest = Request.Builder()
                .url("$baseUrl/setup-token")
                .addHeader("Content-Type", "application/json")
                .post(jsonBody.toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(httpRequest).execute()

            val body = response.body?.string()
            if (!response.isSuccessful || body == null) {
                return Result.failure(Exception("HTTP ${response.code}: $body"))
            }

            val setupToken = Json.decodeFromString<SetupTokenResponse>(body)
            Result.success(setupToken)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
