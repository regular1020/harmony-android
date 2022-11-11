package com.harmony.harmonyAndroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.harmony.harmonyAndroid.activity.SignInActivity
import com.harmony.harmonyAndroid.databinding.ActivityMainBinding
import com.harmony.harmonyAndroid.preference.SharedManager

class MainActivity : AppCompatActivity() {

    private val sharedManager: SharedManager by lazy { SharedManager(this) }
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = sharedManager.getUserData()
        if (user.id == "") {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}