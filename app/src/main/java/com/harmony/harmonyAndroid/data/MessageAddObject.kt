package com.harmony.harmonyAndroid.data

import androidx.lifecycle.MutableLiveData

object MessageAddObject {
    val messageAddLiveData: MutableLiveData<Message> by lazy {
        MutableLiveData<Message>()
    }
}