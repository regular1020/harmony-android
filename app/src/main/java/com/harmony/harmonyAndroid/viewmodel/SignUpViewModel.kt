package com.harmony.harmonyAndroid.viewmodel

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.harmony.harmonyAndroid.data.ModelSignUpComponent
import com.harmony.harmonyAndroid.repository.SignUpRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {
    private val _id = MutableLiveData<String>()
    private val _phone = MutableLiveData<String>()
    private val _pw = MutableLiveData<String>()
    private val _term = MutableLiveData<Boolean>()

    init {
        _id.value = ""
        _phone.value = ""
        _pw.value = ""
        _term.value = false
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignUpViewModel(SignUpRepository.getInstance(application)!!) as T
        }
    }

    val id: LiveData<String>
        get() = _id

    val phone: LiveData<String>
        get() = _phone

    val pw: LiveData<String>
        get() = _pw

    val term: LiveData<Boolean>
        get() = _term

    fun updateID(input: String) {
        _id.value = input
    }

    fun updatePhone(input: String) {
        _phone.value = input
    }

    fun updatePW(input: String) {
        _pw.value = input
    }

    fun updateTerm(input: Boolean) {
        _term.value = input
    }

    private fun checkPW(): Boolean {
        if (_pw.value?.length!! < 6) {
            return false
        }
        val alphabetRegex = "[a-zA-Z]".toRegex()
        val numberRegex = "[0-9]".toRegex()
        val alphabetResult : Sequence<MatchResult> = alphabetRegex.findAll(_pw.value!!)
        val numberResult : Sequence<MatchResult> = numberRegex.findAll(_pw.value!!)
        if (alphabetResult.count() == 0) {
            return false
        }
        if (numberResult.count() == 0) {
            return false
        }
        return true
    }
    private fun checkPhone(): Boolean {
        if (_phone.value?.length!! != 11) {
            return false
        }
        if (_phone.value?.substring(0,3) != "010") {
            return false
        }
        return true
    }
    fun checkInput(context: Context) {
        if (!checkPW()) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("잘못된 비밀번호 형식입니다.")
                .setMessage("정확한 비밀번호를 입력해주십시오.")
                .setPositiveButton("확인") { _, _ -> }

            builder.show()
        } else if (!checkPhone()) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("잘못된 전화번호 형식입니다.")
                .setMessage("정확한 전화번호를 입력해주십시오.")
                .setPositiveButton("확인") { _, _ -> }
            builder.show()
        } else if (!_term.value!!) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("이용 약관에 동의하지 않으셨습니다.")
                .setMessage("이용 약관에 동의해주십시오.")
                .setPositiveButton("확인") { _, _ -> }

            builder.show()
        } else {
            viewModelScope.launch {
                val response = repository.retrofitSignUp(ModelSignUpComponent(_id.value!!, _phone.value!!, _pw.value!!))
                if (response.isSuccessful) {
                    if (response.code() != 200) {
                        val errType = response.body()!!["errorCode"].toString().toInt()%100

                        val builder = AlertDialog.Builder(context)
                        when (errType) {
                            1 -> builder.setTitle("이미 사용중인 아이디입니다.")
                                .setMessage("새로운 아이디를 입력해주십시오.")
                                .setPositiveButton("확인") { _, _ -> }
                            2 -> builder.setTitle("이미 사용중인 전화번호입니다.")
                                .setMessage("새로운 전화번호를 입력해주십시오.")
                                .setPositiveButton("확인") { _, _ -> }
                            else -> builder.setTitle("네트워크 상황이 원할하지 않습니다.")
                                .setMessage("잠시 후 다시 시도해 주십시오.\n지속적으로 반복될 경우 문의주시기 바랍니다.")
                                .setPositiveButton("확인") { _, _ -> }
                        }
                        builder.show()
                    } else {
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("네트워크 상황이 원할하지 않습니다.")
                            .setMessage("잠시 후 다시 시도해 주십시오.\n지속적으로 반복될 경우 문의주시기 바랍니다.")
                            .setPositiveButton("확인") { _, _ -> }
                        builder.show()
                    }
                }
            }
        }
    }
}