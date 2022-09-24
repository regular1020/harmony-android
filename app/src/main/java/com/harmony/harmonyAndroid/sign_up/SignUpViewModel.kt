package com.harmony.harmonyAndroid.sign_up

import android.app.AlertDialog
import android.content.Context
import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    // 아이디, 비밀번호, 전화번호, 이용약관 저장할 livedata
    private val _id = MutableLiveData<String>()
    private val _phone = MutableLiveData<String>()
    private val _pw = MutableLiveData<String>()
    private val _term = MutableLiveData<Boolean>()

    // 초기값 설정
    init {
        _id.value = ""
        _phone.value = ""
        _pw.value = ""
        _term.value = false
    }

    // getter setter
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

    // 유효성 검사 함수
    fun checkID(): Boolean {
        // ID 중복 확인 함수
        // 입력받은 id를 서버에 전달하여 체크
        return true
    }
    fun checkPW(): Boolean {
        // 비밀번호 유효 확인 함수
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
    fun checkPhone(): Boolean {
        // 전화번호 형식 확인 함수
        if (_phone.value?.length!! != 11) {
            return false
        }
        if (_phone.value?.substring(0,3) != "010") {
            return false
        }
        return true
    }
    fun checkInput(context: Context) {
        if (!checkID()) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("사용할 수 없는 ID입니다.")
                .setMessage("다른 ID를 입력해주십시오.")
                .setPositiveButton("확인") { _, _ -> }

            builder.show()
        } else if (!checkPW()) {
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
        }
    }
}