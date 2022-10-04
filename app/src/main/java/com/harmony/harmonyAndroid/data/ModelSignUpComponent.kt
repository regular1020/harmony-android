package com.harmony.harmonyAndroid.data

import com.google.gson.annotations.SerializedName

data class ModelSignUpComponent(
    @SerializedName("id") val id: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("pw") val pw: String
)
