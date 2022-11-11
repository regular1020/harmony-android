package com.harmony.harmonyAndroid.viewmodel

import android.app.AlertDialog
import android.app.Application
import androidx.lifecycle.*
import com.harmony.harmonyAndroid.data.ModelSignInComponent
import com.harmony.harmonyAndroid.data.ModelSignUpComponent
import com.harmony.harmonyAndroid.repository.UserManagementRepository
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: UserManagementRepository) : ViewModel() {
    private val _id = MutableLiveData<String>()
    private val _pw = MutableLiveData<String>()

    init {
        _id.value = ""
        _pw.value = ""
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignInViewModel(UserManagementRepository.getInstance(application)!!) as T
        }
    }

    val id: LiveData<String>
        get() = _id

    val pw: LiveData<String>
        get() = _pw

    fun updateID(input: String) {
        _id.value = input
    }

    fun updatePW(input: String) {
        _pw.value = input
    }

    fun signIn() {
        // TODO : 로그인 구현
        viewModelScope.launch {
            val response = repository.retrofitSignIn(ModelSignInComponent(_id.value!!, _pw.value!!))
            if (response.isSuccessful) {

            }
        }
    }
}