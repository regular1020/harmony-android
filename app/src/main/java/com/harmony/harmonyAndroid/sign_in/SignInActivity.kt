package com.harmony.harmonyAndroid.sign_in

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.harmony.databinding.ActivitySignInBinding
import com.harmony.harmonyAndroid.sign_up.SignUpActivity

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var signInViewModel: SignInViewModel

    private var mBinding: ActivitySignInBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val userInputID = binding.ptSignInId.text.toString()
        val userInputPassword = binding.ptSignInPassword.text.toString()

        signInViewModel.updateID(userInputID)
        signInViewModel.updatePW(userInputPassword)

        signInViewModel.signIn()
    }
}