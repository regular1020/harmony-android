package com.harmony.harmonyAndroid.repository

import android.app.Application
import com.harmony.harmonyAndroid.data.ModelSignInComponent
import com.harmony.harmonyAndroid.data.ModelSignUpComponent
import com.harmony.harmonyAndroid.network.UserManagementObject
import org.json.JSONObject
import retrofit2.Response

class UserManagementRepository(application: Application) {
    companion object {
        private var instance: UserManagementRepository? = null

        fun getInstance(application: Application): UserManagementRepository? {
            if (instance == null) instance = UserManagementRepository(application)
            return instance
        }
    }

    suspend fun retrofitSignUp(modelSingUpComponent: ModelSignUpComponent): Response<JSONObject> {
        return UserManagementObject.getSignUpService.postSignUp(hashMapOf("user_id" to modelSingUpComponent.id, "password" to modelSingUpComponent.pw, "phone_number" to modelSingUpComponent.phone))
    }

    suspend fun retrofitSignIn(modelSignInComponent: ModelSignInComponent): Response<JSONObject> {
        return UserManagementObject.getSignInService.postSignIn(hashMapOf("user_id" to modelSignInComponent.id, "password" to modelSignInComponent.pw))
    }
}