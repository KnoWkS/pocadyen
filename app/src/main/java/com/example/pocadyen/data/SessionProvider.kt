package com.example.pocadyen.data

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


import kotlinx.serialization.Serializable

@Serializable
data class SessionRequest(val setupToken: String)

@Serializable
data class SessionResponse(
    val id: String,
    val installationId: String,
    val sdkData: String
)

class SessionProvider(
    private val client: OkHttpClient = OkHttpClient(),
    private val baseUrl: String = "http://192.168.1.12:8080"
) {
    suspend fun createSession(setupToken: String): Result<SessionResponse> {
        return try {
            val body = Json.encodeToString(SessionRequest(setupToken))
                .toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url("$baseUrl/sessions")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            val json = response.body?.string()

            if (!response.isSuccessful || json == null) {
                return Result.failure(Exception("HTTP ${response.code}: $json"))
            }

            val session = Json.decodeFromString(SessionResponse.serializer(), json)
            Result.success(session)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
