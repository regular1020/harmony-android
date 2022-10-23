package com.harmony.harmonyAndroid.network

import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignUpInterface {
    @Headers("accept: application/json", "content-type: application/json")
    @POST("/member/join")
    suspend fun postSignUp(
        @Body params: HashMap<String, Any>
    ): Response<JSONObject>
}