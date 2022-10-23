package com.harmony.harmonyAndroid.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SignUpObject {
    private const val signUpURL = "http://115.85.181.222:8080"

    private val getRetrofit by lazy{
        Retrofit.Builder()
            .baseUrl(signUpURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getRetrofitService : SignUpInterface = getRetrofit.create(SignUpInterface::class.java)
}