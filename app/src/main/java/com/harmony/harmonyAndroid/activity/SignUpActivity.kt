package com.harmony.harmonyAndroid.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.harmony.databinding.ActivitySignUpBinding
import com.harmony.harmonyAndroid.MainActivity
import com.harmony.harmonyAndroid.viewmodel.SignUpViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, SignUpViewModel.Factory(application))[SignUpViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.btnSignup.setOnClickListener(this)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigate.observe(this) {
            if (it == true) {
                finish()
            }
        }
    }

    override fun onClick(view: View?) {
        if (view!!.id == binding.btnSignup.id) {
            val userInputID = binding.ptSignUpId.text.toString()
            val userInputPW = binding.ptSignUpPassword.text.toString()
            val userInputPhone = binding.ptSignUpPhone.text.toString()
            val userCheckTerm = binding.cbTermOfService.isChecked

            viewModel.updateID(userInputID)
            viewModel.updatePW(userInputPW)
            viewModel.updatePhone(userInputPhone)
            viewModel.updateTerm(userCheckTerm)

            viewModel.checkInput(this)
        }
    }
}