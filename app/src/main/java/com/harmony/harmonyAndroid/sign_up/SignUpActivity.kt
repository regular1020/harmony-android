package com.harmony.harmonyAndroid.sign_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.harmony.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var signUpViewModel: SignUpViewModel

    private var mBinding: ActivitySignUpBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)
        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        binding.btnSignup.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val userInputID = binding.ptSignUpId.text.toString()
        val userInputPW = binding.ptSignUpPassword.text.toString()
        val userInputPhone = binding.ptSignUpPhone.text.toString()
        val userCheckTerm = binding.cbTermOfService.isChecked

        signUpViewModel.updateID(userInputID)
        signUpViewModel.updatePW(userInputPW)
        signUpViewModel.updatePhone(userInputPhone)
        signUpViewModel.updateTerm(userCheckTerm)

        signUpViewModel.checkInput(this)
    }
}