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
        return UserManagementObject.getSignUpService.postSignUp(hashMapOf("user_id" to modelSingUpComponent.id, "password" to modelSingUpComponent.pw, "phone_number" to modelSingUpComponent.phone))
    }

    suspend fun retrofitSignIn(modelSignInComponent: ModelSignInComponent): Response<JsonObject> {
        return UserManagementObject.getSignInService.postSignIn(hashMapOf("user_id" to modelSignInComponent.id, "password" to modelSignInComponent.pw))
    }

    suspend fun retrofitDuplicateCheckByID(modelDuplicateCheckByIDComponent: ModelDuplicateCheckByIDComponent): Response<JsonObject> {
        return UserManagementObject.getSignUpService.getMemberByID(modelDuplicateCheckByIDComponent.user_id)
    }

    suspend fun retrofitDuplicateCheckByPhoneNumber(modelDuplicateCheckByPhoneNumberComponent: ModelDuplicateCheckByPhoneNumberComponent): Response<JsonObject> {
        return UserManagementObject.getSignUpService.getMemberByPhone(modelDuplicateCheckByPhoneNumberComponent.phone_number)
    }
}