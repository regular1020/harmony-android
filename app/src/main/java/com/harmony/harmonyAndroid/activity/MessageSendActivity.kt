package com.harmony.harmonyAndroid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.harmony.harmonyAndroid.R
import com.harmony.harmonyAndroid.databinding.ActivityMessageSendBinding

class MessageSendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageSendBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMessageSendBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.message_send_toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.send_message_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.send_message -> {
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}