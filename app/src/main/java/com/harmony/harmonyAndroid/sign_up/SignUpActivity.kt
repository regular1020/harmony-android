package com.harmony.harmonyAndroid.sign_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.harmony.R
import com.example.harmony.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private var mBinding: ActivitySignUpBinding? = null
    private val binding get() = mBinding!!

    private fun checkID(id: String): Boolean {
        // ID 중복 확인 함수
        // 입력받은 id를 서버에 전달하여 체크
        return true
    }

    private fun checkPW(pw: String): Boolean {
        // 비밀번호 유효 확인 함수
        if (pw.length < 6) {
            return false
        }
        val alphabetRegex = "[a-zA-Z]".toRegex()
        val numberRegex = "[0-9]".toRegex()
        val alphabetResult : Sequence<MatchResult> = alphabetRegex.findAll(pw)
        val numberResult : Sequence<MatchResult> = numberRegex.findAll(pw)
        if (alphabetResult.count() == 0) {
            return false
        }
        if (numberResult.count() == 0) {
            return false
        }
        return true
    }

    private fun checkPhone(phone: String): Boolean {
        // 전화번호 형식 확인 함수
        if (phone.length != 11) {
            return false
        }
        if (phone.substring(0,3) != "010") {
            return false
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener{
            val id = binding.ptId.text.toString()
            val pw = binding.ptPassword.text.toString()
            val phone = binding.ptPhone.text.toString()
            val term = binding.cbTermOfService.isChecked

            if (!checkID(id)) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("사용할 수 없는 ID입니다.")
                    .setMessage("다른 ID를 입력해주십시오.")
                    .setPositiveButton("확인") { _, _ -> }

                builder.show()
            } else if (!checkPW(pw)) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("잘못된 비밀번호 형식입니다.")
                    .setMessage("정확한 비밀번호를 입력해주십시오.")
                    .setPositiveButton("확인") { _, _ -> }

                builder.show()
            } else if (!checkPhone(phone)) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("잘못된 전화번호 형식입니다.")
                    .setMessage("정확한 전화번호를 입력해주십시오.")
                    .setPositiveButton("확인") { _, _ -> }

                builder.show()
            } else if (!term) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("이용 약관에 동의하지 않으셨습니다.")
                    .setMessage("이용 약관에 동의해주십시오.")
                    .setPositiveButton("확인") { _, _ -> }

                builder.show()
            }
        }
    }
}