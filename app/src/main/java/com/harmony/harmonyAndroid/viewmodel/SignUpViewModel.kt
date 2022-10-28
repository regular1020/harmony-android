package com.harmony.harmonyAndroid.viewmodel

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.harmony.harmonyAndroid.data.ModelDuplicateCheckByIDComponent
import com.harmony.harmonyAndroid.data.ModelDuplicateCheckByPhoneNumberComponent
import com.harmony.harmonyAndroid.data.ModelSignUpComponent
import com.harmony.harmonyAndroid.repository.UserManagementRepository
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SignUpViewModel(private val repository: UserManagementRepository) : ViewModel() {
    private val _id = MutableLiveData<String>()
    private val _phone = MutableLiveData<String>()
    private val _pw = MutableLiveData<String>()
    private val _pwCheck = MutableLiveData<String>()
    private val _term = MutableLiveData<Boolean>()
    private var _navigate = MutableLiveData<Boolean>()
    private var _hashedPW = MutableLiveData<String>()

    init {
        _id.value = ""
        _phone.value = ""
        _pw.value = ""
        _pwCheck.value = ""
        _term.value = false
        _navigate.value = false
        _hashedPW.value = ""
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignUpViewModel(UserManagementRepository.getInstance(application)!!) as T
        }
    }

    val id: LiveData<String>
        get() = _id

    val phone: LiveData<String>
        get() = _phone

    val pw: LiveData<String>
        get() = _pw

    val pwCheck: LiveData<String>
        get() = _pwCheck

    val term: LiveData<Boolean>
        get() = _term

    val navigate: LiveData<Boolean>
        get() = _navigate

    fun updateID(input: String) {
        _id.value = input
    }

    fun updatePhone(input: String) {
        _phone.value = input
    }

    fun updatePW(input: String) {
        _pw.value = input
    }

    fun updatePWCheck(input: String) {
        _pwCheck.value = input
    }

    fun updateTerm(input: Boolean) {
        _term.value = input
    }

    fun updateNavigate(input: Boolean) {
        _navigate.value = input
    }

    fun idDuplicateCheck(context: Context) {
        viewModelScope.launch {
            val response = repository.retrofitDuplicateCheckByID(
                ModelDuplicateCheckByIDComponent(_id.value!!)
            )
            val builder = AlertDialog.Builder(context)
            if (response.isSuccessful) {
                if (response.code() != 200) {
                    if (response.body()!!.has("error_code")) {
                        val errType = response.body()!!["errorCode"].toString().toInt()%100
                        when (errType) {
                            else -> builder.setTitle("네트워크 상황이 원할하지 않습니다.")
                                .setMessage("잠시 후 다시 시도해 주십시오.\n지속적으로 반복될 경우 문의주시기 바랍니다.")
                                .setPositiveButton("확인") { _, _ -> }
                        }
                    }
                } else {
                    if (listOf(response.body()!!["data"]).isNotEmpty()) {
                        builder.setTitle("이미 사용중인 아이디입니다.")
                            .setMessage("새로운 아이디를 입력해주십시오.")
                            .setPositiveButton("확인") { _, _ -> }
                    } else {
                        builder.setTitle("사용 가능한 아이디입니다.")
                            .setPositiveButton("확인") { _, _ -> }
                    }
                }
            } else {
                builder.setTitle("네트워크 상황이 원할하지 않습니다.")
                .setMessage("잠시 후 다시 시도해 주십시오.\n지속적으로 반복될 경우 문의주시기 바랍니다.")
                .setPositiveButton("확인") { _, _ -> }
            }
            builder.show()
        }
    }

    fun phoneDuplicateCheck(context: Context) {
        viewModelScope.launch {
            val response = repository.retrofitDuplicateCheckByPhoneNumber(
                ModelDuplicateCheckByPhoneNumberComponent(_phone.value!!)
            )
            val builder = AlertDialog.Builder(context)
            if (response.isSuccessful) {
                if (response.code() != 200) {
                    if (response.body()!!.has("error_code")) {
                        val errType = response.body()!!["errorCode"].toString().toInt()%100
                        when (errType) {
                            else -> builder.setTitle("네트워크 상황이 원할하지 않습니다.")
                                .setMessage("잠시 후 다시 시도해 주십시오.\n지속적으로 반복될 경우 문의주시기 바랍니다.")
                                .setPositiveButton("확인") { _, _ -> }
                        }
                    }
                } else {
                    if (listOf(response.body()!!["data"]).isNotEmpty()) {
                        builder.setTitle("이미 사용중인 전화번호입니다.")
                            .setMessage("새로운 전화번호를 입력해주십시오.")
                            .setPositiveButton("확인") { _, _ -> }
                    } else {
                        builder.setTitle("사용 가능한 전화번호입니다.")
                            .setPositiveButton("확인") { _, _ -> }
                    }
                }
            } else {
                builder.setTitle("네트워크 상황이 원할하지 않습니다.")
                    .setMessage("잠시 후 다시 시도해 주십시오.\n지속적으로 반복될 경우 문의주시기 바랍니다.")
                    .setPositiveButton("확인") { _, _ -> }
            }
            builder.show()
        }
    }

    fun hashPassWord() {
        var digest: String = ""
        digest = try {
            val sh = MessageDigest.getInstance("SHA-256")
            sh.update(_pw.value!!.toByteArray())
            val byteData = sh.digest()

            val hexChars = "0123456789ABCDEF"
            val hex = CharArray(byteData.size * 2)
            for (i in byteData.indices) {
                val v = byteData[i].toInt() and 0xff
                hex[i * 2] = hexChars[v shr 4]
                hex[i * 2 + 1] = hexChars[v and 0xf]
            }

            String(hex) //최종 결과를 String 으로 변환
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            ""
        }

        _hashedPW.value = digest
        Log.d("비밀번호 해시", digest)
    }

    fun signUp(context: Context) {
        if (!_term.value!!) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("이용 약관에 동의하지 않으셨습니다.")
                .setMessage("이용 약관에 동의해주십시오.")
                .setPositiveButton("확인") { _, _ -> }

            builder.show()
        } else if (_pw.value != _pwCheck.value) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("비밀번호 확인 일치하지 않습니다.")
                .setMessage("비밀번호와 비밀번호 확인을 일치하게 입력해주십시오")
                .setPositiveButton("확인") { _, _ -> }

            builder.show()
        }
        else {
            viewModelScope.launch {
                val response = repository.retrofitSignUp(ModelSignUpComponent(_id.value!!, _phone.value!!, _hashedPW.value!!))
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        _navigate.value = true
                    } else {
                        if (response.body()!!.has("errorCode")) {
                            val errType = response.body()!!["errorCode"].toString().toInt()%100

                            val builder = AlertDialog.Builder(context)
                            when (errType) {
                                1 -> builder.setTitle("이미 사용중인 아이디입니다.")
                                    .setMessage("새로운 아이디를 입력해주십시오.")
                                    .setPositiveButton("확인") { _, _ -> }
                                2 -> builder.setTitle("이미 사용중인 전화번호입니다.")
                                    .setMessage("새로운 전화번호를 입력해주십시오.")
                                    .setPositiveButton("확인") { _, _ -> }
                                3 -> builder.setTitle("잘못된 아이디 형식입니다.")
                                    .setMessage("아이디는 알파벳 소문자, 숫자가 허용됩니다. \n아이디 첫 글자는 소문자 알파벳이어야 합니다.")
                                    .setPositiveButton("확인") { _, _ -> }
                                else -> builder.setTitle("네트워크 상황이 원할하지 않습니다.")
                                    .setMessage("잠시 후 다시 시도해 주십시오.\n지속적으로 반복될 경우 문의주시기 바랍니다.")
                                    .setPositiveButton("확인") { _, _ -> }
                            }
                            builder.show()
                        }
                    }
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