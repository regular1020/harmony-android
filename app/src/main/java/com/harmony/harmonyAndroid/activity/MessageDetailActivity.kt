package com.harmony.harmonyAndroid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.harmony.harmonyAndroid.data.Message
import com.harmony.harmonyAndroid.data.MessageDeleteObject
import com.harmony.harmonyAndroid.databinding.ActivityMessageDetailBinding
import com.harmony.harmonyAndroid.viewmodel.MessageDetailViewModel

class MessageDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageDetailBinding
    private val viewModel by lazy { ViewModelProvider(this, MessageDetailViewModel.Factory(application))[MessageDetailViewModel::class.java] }
    lateinit var message : Message

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        message = intent.getParcelableExtra<Message>("data")!!

        binding.tvTitle.text = message.title
        binding.tvContents.text = message.content
        binding.tvTimestamp.text = message.timestamp.split(" ")[0]

        binding.btnDelete.setOnClickListener {
            viewModel.deleteMessage(message)
        }

        setUpObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        MessageDeleteObject.messageDeleteLiveData.value = null
    }

    private fun setUpObserver() {
        viewModel.isMessageDeleted.observe(this) {
            if (it) {
                viewModel.isMessageDeleted.value = false
                onBackPressed()
            }
        }
    }
}