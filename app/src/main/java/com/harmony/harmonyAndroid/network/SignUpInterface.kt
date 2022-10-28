package com.harmony.harmonyAndroid.network

import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface SignUpInterface {
    @Headers("accept: application/json", "content-type: application/json")

    @POST("/member/join")
    suspend fun postSignUp(
        @Body params: HashMap<String, Any>
    ): Response<JSONObject>

    @GET("/member")
    suspend fun getMemberByID(
        @Query("user_id") user_id:String
    ): Response<JSONObject>

    @GET("/member")
    suspend fun getMemberByPhone(
        @Query("phone_number") phone_number:String
    ): Response<JSONObject>
}