package com.harmony.harmonyAndroid.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.harmony.databinding.ActivitySignInBinding
import com.example.harmony.databinding.ActivitySignUpBinding
import com.harmony.harmonyAndroid.viewmodel.SignInViewModel
import com.harmony.harmonyAndroid.viewmodel.SignUpViewModel

class SignInActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, SignInViewModel.Factory(application))[SignInViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view!!.id) {
            binding.btnSignUp.id -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
            binding.btnSignIn.id -> {
                val userInputID = binding.ptSignInId.text.toString()
                val userInputPassword = binding.ptSignInPassword.text.toString()

                viewModel.updateID(userInputID)
                viewModel.updatePW(userInputPassword)

                viewModel.signIn()
            }
            binding.btnFindId.id -> {

            }
            binding.btnFindPw.id -> {

            }
        }
    }
}