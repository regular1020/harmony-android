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
import org.json.JSONException
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

    fun idDuplicateCheck(context: Context) {
        if (_id.value == "") {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("ID를 입력하지 않으셨습니다.")
                .setMessage("ID를 입력해주십시오.")
                .setPositiveButton("확인") { _, _ -> }

            builder.show()
            return
        }
        viewModelScope.launch {
            val response = repository.retrofitDuplicateCheckByID(
                ModelDuplicateCheckByIDComponent(_id.value!!)
            )
            val builder = AlertDialog.Builder(context)
            if (response.isSuccessful) {
                if (response.body()!!["data"].asJsonArray.size() > 0) {
                    Log.d("test", response.body()!!["data"].asJsonArray.size().toString())
                    builder.setTitle("이미 사용중인 아이디입니다.")
                        .setMessage("새로운 아이디를 입력해주십시오.")
                        .setPositiveButton("확인") { _, _ -> }
                    builder.show()
                    return@launch
                }
                builder.setTitle("사용 가능한 아이디입니다.")
                    .setPositiveButton("확인") { _, _ -> }
                builder.show()
                return@launch
            }
            if (response.code() in 500..599) {
                builder.setTitle("서버 오류입니다.")
                    .setMessage("관리자에게 문의해주십시오.")
                    .setPositiveButton("확인") { _, _ -> }
                return@launch
            }
            var errorObject: JSONObject? = null
            try {
                errorObject = JSONObject(response.errorBody()!!.string())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            if (errorObject != null) {
                var errorMessage = ""

                val errorArray = errorObject.getJSONArray("errors")

                var i = 0

                while (i < errorArray.length()) {
                    val jsonObject = errorArray.getJSONObject(i)
                    val errorCode = jsonObject["code"]
                    if (errorCode == "NotFound")
                        errorMessage += "ID를 입력해주세요.\n"
                    i++
                }
                builder.setTitle("다시 입력해주십시오.")
                    .setMessage(errorMessage)
                    .setPositiveButton("확인") { _, _ -> }

                builder.show()

                return@launch
            }
        }
    }

    fun phoneDuplicateCheck(context: Context) {
        if (_phone.value == "") {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("전화번호를 입력하지 않으셨습니다.")
                .setMessage("전화번호를 입력해주십시오.")
                .setPositiveButton("확인") { _, _ -> }

            builder.show()
            return
        }
        viewModelScope.launch {
            val response = repository.retrofitDuplicateCheckByPhoneNumber(
                ModelDuplicateCheckByPhoneNumberComponent(_phone.value!!)
            )
            val builder = AlertDialog.Builder(context)
            if (response.isSuccessful) {
                if (response.body()!!["data"].asJsonArray.size() > 0) {
                    builder.setTitle("이미 사용중인 전화번호입니다.")
                        .setMessage("새로운 전화번호를 입력해주십시오.")
                        .setPositiveButton("확인") { _, _ -> }
                    builder.show()
                    return@launch
                }
                builder.setTitle("중복되지 않는 전화번호입니다.")
                    .setPositiveButton("확인") { _, _ -> }
                builder.show()
                return@launch
            }
            if (response.code() in 500..599) {
                builder.setTitle("서버 오류입니다.")
                    .setMessage("관리자에게 문의해주십시오.")
                    .setPositiveButton("확인") { _, _ -> }
                return@launch
            }
            var errorObject: JSONObject? = null
            try {
                errorObject = JSONObject(response.errorBody()!!.string())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            if (errorObject != null) {
                var errorMessage = ""

                val errorArray = errorObject.getJSONArray("errors")

                var i = 0

                while (i < errorArray.length()) {
                    val jsonObject = errorArray.getJSONObject(i)
                    val errorCode = jsonObject["code"]
                    if (errorCode == "NotFound")
                        errorMessage += "전화번호를 입력해주세요.\n"
                    i++
                }
                builder.setTitle("다시 입력해주십시오.")
                    .setMessage(errorMessage)
                    .setPositiveButton("확인") { _, _ -> }

                builder.show()

                return@launch
            }
        }
    }

    private fun checkPassWord(): Boolean {
        val regex = Regex("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{6,20}$")
        return regex.matches(_pw.value.toString())
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
        if (!checkPassWord()) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("형식에 맞지 않는 비밀번호입니다.")
                .setMessage("알파벳 소문자와 숫자로 이루어진 6~20자리 비밀번호여야 합니다.")
                .setPositiveButton("확인") { _, _ -> }

            builder.show()
            return
        }
        if (_id.value == "") {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("ID를 입력하지 않으셨습니다.")
                .setMessage("ID를 입력해주십시오.")
                .setPositiveButton("확인") { _, _ -> }

            builder.show()
            return
        }
        if (_phone.value == "") {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("전화번호를 입력하지 않으셨습니다.")
                .setMessage("전화번호를 입력해주십시오.")
                .setPositiveButton("확인") { _, _ -> }

            builder.show()
            return
        }
        if (!_term.value!!) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("이용 약관에 동의하지 않으셨습니다.")
                .setMessage("이용 약관에 동의해주십시오.")
                .setPositiveButton("확인") { _, _ -> }

            builder.show()
            return
        }
        if (_pw.value != _pwCheck.value) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("비밀번호 확인 일치하지 않습니다.")
                .setMessage("비밀번호와 비밀번호 확인을 일치하게 입력해주십시오")
                .setPositiveButton("확인") { _, _ -> }

            builder.show()
            return
        }
        viewModelScope.launch {
            val response = repository.retrofitSignUp(ModelSignUpComponent(_id.value!!, _phone.value!!, _hashedPW.value!!))
            val builder = AlertDialog.Builder(context)
            if (response.isSuccessful) {
                _navigate.value = true
                return@launch
            }
            var errorObject: JSONObject? = null
            try {
                errorObject = JSONObject(response.errorBody()!!.string())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            if (errorObject != null) {
                var errorMessage = ""

                val errorArray = errorObject.getJSONArray("errors")

                var i = 0

                while (i < errorArray.length()) {
                    val jsonObject = errorArray.getJSONObject(i)
                    val errorField = jsonObject["field"]
                    val errorCode = jsonObject["code"]
                    if (errorField == "userId" && errorCode == "Duplicated")
                        errorMessage += "사용중인 ID입니다.\n"
                    if (errorField == "phoneNumber" && errorCode == "Duplicated")
                        errorMessage += "사용중인 전화번호입니다.\n"
                    if (errorField == "userId" && errorCode == "Pattern")
                        errorMessage += "잘못된 ID형식입니다.\n"
                    if (errorField == "phoneNumber" && errorCode == "Pattern")
                        errorMessage += "잘못된 전화번호 형식입니다.\n"
                    i++
                }
                builder.setTitle("다시 입력해주십시오.")
                    .setMessage(errorMessage)
                    .setPositiveButton("확인") { _, _ -> }

                builder.show()

                return@launch
            }
        }
    }
}