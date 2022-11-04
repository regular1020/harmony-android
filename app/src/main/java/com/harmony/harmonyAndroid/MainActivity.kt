package com.harmony.harmonyAndroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.google.firebase.messaging.FirebaseMessaging
import com.harmony.harmonyAndroid.activity.MessageBoxActivity
import com.harmony.harmonyAndroid.data.Message
import com.harmony.harmonyAndroid.data.UserData
import com.harmony.harmonyAndroid.database.GlobalApplication
import com.harmony.harmonyAndroid.database.MessageDataBase
import com.harmony.harmonyAndroid.databinding.ActivityMainBinding
import com.harmony.harmonyAndroid.viewmodel.MessageBoxViewModel

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("token", task.result)
            }
        }

        GlobalApplication.prefs.saveUserData(UserData("test01", "01012345678"))

        binding.btnMessage.setOnClickListener {
            val intent = Intent(this, MessageBoxActivity::class.java)
            startActivity(intent)
        }
    }
}