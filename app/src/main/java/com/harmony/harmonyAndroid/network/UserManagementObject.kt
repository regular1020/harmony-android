package com.harmony.harmonyAndroid.network

import com.example.harmony.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserManagementObject {
    private const val signUpURL = BuildConfig.SERVER

    private val getRetrofit by lazy{
        Retrofit.Builder()
            .baseUrl(signUpURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getSignUpService : SignUpInterface = getRetrofit.create(SignUpInterface::class.java)

    val getSignInService : SignInInterface = getRetrofit.create(SignInInterface::class.java)
}