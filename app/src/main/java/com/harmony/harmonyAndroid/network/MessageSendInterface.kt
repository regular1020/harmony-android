package com.harmony.harmonyAndroid.network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface MessageSendInterface {
    @Headers("accept: application/json", "content-type: application/json")

    @POST("/message")
    suspend fun postMessage(
        @Body params: HashMap<String, Any>
    ): Response<JsonObject>
}