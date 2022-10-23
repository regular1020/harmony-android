package com.harmony.harmonyAndroid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.harmony.harmonyAndroid.databinding.ActivityMessageBinding

class MessageActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMessageBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}