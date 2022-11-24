package com.harmony.harmonyAndroid.network

import com.harmony.harmonyAndroid.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MessageObject {
    private const val messageURL = BuildConfig.SERVER

    private val getRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(messageURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getMessageSendService : MessageSendInterface = getRetrofit.create(MessageSendInterface::class.java)
}