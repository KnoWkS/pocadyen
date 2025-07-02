package com.example.pocadyen.service

import com.adyen.ipp.authentication.AuthenticationProvider
import com.adyen.ipp.authentication.MerchantAuthenticationService

class MerchantAuthenticationServiceImpl : MerchantAuthenticationService() {

    override lateinit var authenticationProvider: AuthenticationProvider

    override fun onCreate() {
        super.onCreate()
        authenticationProvider = AuthenticationProviderImpl()
    }
}
