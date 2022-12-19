package com.harmony.harmonyAndroid.repository

import android.app.Application
import com.google.gson.JsonObject
import com.harmony.harmonyAndroid.data.ModelDuplicateCheckByIDComponent
import com.harmony.harmonyAndroid.data.ModelDuplicateCheckByPhoneNumberComponent
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

    suspend fun retrofitSignUp(modelSingUpComponent: ModelSignUpComponent): Response<JsonObject> {
        return UserManagementObject.getSignUpService.postSignUp(hashMapOf("userId" to modelSingUpComponent.userId, "password" to modelSingUpComponent.password, "phoneNumber" to modelSingUpComponent.phoneNumber))
    }

    suspend fun retrofitSignIn(modelSignInComponent: ModelSignInComponent): Response<JsonObject> {
        return UserManagementObject.getSignInService.postSignIn(hashMapOf("userId" to modelSignInComponent.userId, "password" to modelSignInComponent.password))
    }

    suspend fun retrofitDuplicateCheckByID(modelDuplicateCheckByIDComponent: ModelDuplicateCheckByIDComponent): Response<JsonObject> {
        return UserManagementObject.getSignUpService.getMemberByID(modelDuplicateCheckByIDComponent.userId)
    }

    suspend fun retrofitDuplicateCheckByPhoneNumber(modelDuplicateCheckByPhoneNumberComponent: ModelDuplicateCheckByPhoneNumberComponent): Response<JsonObject> {
        return UserManagementObject.getSignUpService.getMemberByPhone(modelDuplicateCheckByPhoneNumberComponent.phoneNumber)
    }
}