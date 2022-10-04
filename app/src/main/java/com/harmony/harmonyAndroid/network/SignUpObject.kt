package com.harmony.harmonyAndroid.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SignUpObject {
    private const val signUpURL = ""

    private val okHttpClient = OkHttpClient.Builder().build()

    private val getRetrofit by lazy{
        Retrofit.Builder()
            .baseUrl(signUpURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val getRetrofitService : SignUpInterface by lazy {
       getRetrofit.create(SignUpInterface::class.java)
    }
}