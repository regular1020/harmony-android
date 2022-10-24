package com.harmony.harmonyAndroid.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserManagementObject {
    private const val signUpURL = "http://115.85.181.222:8080"

    private val getRetrofit by lazy{
        Retrofit.Builder()
            .baseUrl(signUpURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getSignUpService : SignUpInterface = getRetrofit.create(SignUpInterface::class.java)

    val getSignInService : SignInInterface = getRetrofit.create(SignInInterface::class.java)
}