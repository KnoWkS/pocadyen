package com.example.pocadyen.service

import com.adyen.ipp.authentication.MerchantAuthenticationService
import com.adyen.ipp.authentication.AuthenticationResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class MerchantAuthenticationServiceImpl : MerchantAuthenticationService {
    override suspend fun fetchSession(setupToken: String): Result<AuthenticationResponse> {
        // 1. Crée un objet HTTP
        val client = OkHttpClient()

        // 2. Prépare un corps JSON
        val json = JSONObject().apply {
            put("setupToken", setupToken)
        }

        // 3. Convertit en format HTTP compatible
        val body = json.toString()
            .toRequestBody("application/json".toMediaType())

        // 4. Crée une requête HTTP POST vers ton backend
        val request = Request.Builder()
            .url("http://10.0.2.2:8080/sessions") // localhost simulé
            .post(body)
            .build()

        // 5. Envoie la requête
        val response = client.newCall(request).execute()

        // 6. Si erreur (ex: serveur éteint)
        if (!response.isSuccessful) {
            return Result.failure(Exception("Erreur ${response.code}"))
        }

        // 7. Lit le champ "sdkData" de la réponse
        val responseBody = response.body?.string() ?: ""
        val sdkData = JSONObject(responseBody).getString("sdkData")

        // 8. Transforme en réponse pour le SDK Adyen
        return Result.success(com.adyen.ipp.authentication.AuthenticationResponse(sdkData))
    }
}
