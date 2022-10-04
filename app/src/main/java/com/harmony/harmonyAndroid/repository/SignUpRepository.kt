package com.harmony.harmonyAndroid.repository

import android.app.Application
import com.harmony.harmonyAndroid.data.ModelSignUpComponent
import com.harmony.harmonyAndroid.network.SignUpObject
import org.json.JSONObject
import retrofit2.Response

class SignUpRepository(application: Application) {
    companion object {
        private var instance: SignUpRepository? = null

        fun getInstance(application: Application): SignUpRepository? {
            if (instance == null) instance = SignUpRepository(application)
            return instance
        }
    }

    suspend fun retrofitSignUp(modelSingUpComponent: ModelSignUpComponent): Response<JSONObject> {
        return SignUpObject.getRetrofitService.postSignUp(modelSingUpComponent.id, modelSingUpComponent.phone, modelSingUpComponent.pw)
    }
}