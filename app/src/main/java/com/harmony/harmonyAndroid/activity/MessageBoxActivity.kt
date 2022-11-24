package com.harmony.harmonyAndroid.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.harmony.harmonyAndroid.R
import com.harmony.harmonyAndroid.adapter.MessageAdapter
import com.harmony.harmonyAndroid.data.Message
import com.harmony.harmonyAndroid.database.GlobalApplication
import com.harmony.harmonyAndroid.databinding.ActivityMessageBoxBinding
import com.harmony.harmonyAndroid.data.MessageAddObject
import com.harmony.harmonyAndroid.data.MessageDeleteObject
import com.harmony.harmonyAndroid.viewmodel.MessageBoxViewModel

class MessageBoxActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this, MessageBoxViewModel.Factory(application))[MessageBoxViewModel::class.java] }
    private lateinit var binding: ActivityMessageBoxBinding
    lateinit var messageList: MutableList<Message>
    lateinit var messageRecyclerViewAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMessageBoxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.messageBoxToolbar)

        binding.btnSendNewMessage.setOnClickListener{
            val intent = Intent(this, MessageSendActivity::class.java)
            startActivity(intent)
        }

        getAllMessage()
        setUpObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        MessageAddObject.messageAddLiveData.value = null
    }

    private fun setUpObserver() {
        viewModel.isGetMessageByReceiverIDComplete.observe(this) {
            messageList = it.toMutableList()
            setUpRecyclerView()
        }

        MessageAddObject.messageAddLiveData.observe(this, Observer<Message> {
            if (it != null) {
                messageList.add(it)
                messageRecyclerViewAdapter.notifyItemInserted(0)
            }
        })

        MessageDeleteObject.messageDeleteLiveData.observe(this) {
            if (it != null) {
                var position = -1
                for (i in 0 until messageList.size) {
                    if (messageList[i].id == it.id) {
                        position = i
                        break
                    }
                }
                messageList.removeAt(position)
                messageRecyclerViewAdapter.notifyItemRemoved(position)
                messageRecyclerViewAdapter.notifyItemChanged(position)
            }
        }
    }

    private fun getAllMessage() {
        viewModel.getMessageByReceiverID(GlobalApplication.prefs.getUserData().id.toString())
    }

    private fun setUpRecyclerView() {
        messageRecyclerViewAdapter = MessageAdapter(baseContext, messageList, viewModel)

        binding.messageRecyclerView.adapter = messageRecyclerViewAdapter
        binding.messageRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}