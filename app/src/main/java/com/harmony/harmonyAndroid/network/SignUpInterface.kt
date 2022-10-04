package com.harmony.harmonyAndroid.network

import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.POST

interface SignUpInterface {
    @POST("post_user_info")
    suspend fun postSignUp(
        @Field("id") id: String,
        @Field("phone") phone: String,
        @Field("pw") pw: String
    ): Response<JSONObject>
}