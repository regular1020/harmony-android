package com.harmony.harmonyAndroid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.harmony.databinding.ActivitySignUpBinding
import com.harmony.harmonyAndroid.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, SignUpViewModel.Factory(application))[SignUpViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.btnSignup.setOnClickListener(this)
        binding.btnIdCheck.setOnClickListener(this)
        binding.btnPhoneCheck.setOnClickListener(this)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigate.observe(this) {
            if (it == true) {
                finish()
            }
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            binding.btnSignup.id -> {
                val userInputID = binding.ptSignUpId.text.toString()
                val userInputPW = binding.ptSignUpPassword.text.toString()
                val userInputPhone = binding.ptSignUpPhone.text.toString()
                val userInputPWCheck = binding.ptSignUpPasswordCheck.text.toString()
                val userCheckTerm = binding.cbTermOfService.isChecked

                viewModel.updateID(userInputID)
                viewModel.updatePW(userInputPW)
                viewModel.updatePWCheck(userInputPWCheck)
                viewModel.updatePhone(userInputPhone)
                viewModel.updateTerm(userCheckTerm)

                viewModel.hashPassWord()

                viewModel.signUp(this)
            }
            binding.btnIdCheck.id -> {
                val userInputID = binding.ptSignUpId.text.toString()

                viewModel.updateID(userInputID)

                viewModel.idDuplicateCheck(this)
            }
            binding.btnPhoneCheck.id -> {
                val userInputPhone = binding.ptSignUpPhone.text.toString()

                viewModel.updatePhone(userInputPhone)

                viewModel.phoneDuplicateCheck(this)
            }
            else -> null
        }
    }
}