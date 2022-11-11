package com.harmony.harmonyAndroid.network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignInInterface {
    @Headers("accept: application/json", "content-type: application/json")

    @POST("/member/auth")
    suspend fun postSignIn(
        // TODO : 로그인 api 통신 구현
        @Body params: HashMap<String, Any>
    ): Response<JsonObject>
}