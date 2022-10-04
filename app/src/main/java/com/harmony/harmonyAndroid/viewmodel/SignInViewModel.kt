package com.harmony.harmonyAndroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {
    private val _id = MutableLiveData<String>()
    private val _pw = MutableLiveData<String>()

    init {
        _id.value = ""
        _pw.value = ""
    }

    // getter
    val id: LiveData<String>
        get() = _id

    val pw: LiveData<String>
        get() = _pw

    // setter
    fun updateID(input: String) {
        _id.value = input
    }

    fun updatePW(input: String) {
        _pw.value = input
    }

    // 로그인 진행
    fun signIn() {

    }
}