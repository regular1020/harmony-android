package com.harmony.harmonyAndroid.data

import androidx.lifecycle.MutableLiveData

object MessageDeleteObject {
    val messageDeleteLiveData: MutableLiveData<Message> by lazy {
        MutableLiveData<Message>()
    }
}