package com.harmony.harmonyAndroid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.harmony.harmonyAndroid.adapter.MessageAdapter
import com.harmony.harmonyAndroid.data.Message
import com.harmony.harmonyAndroid.database.GlobalApplication
import com.harmony.harmonyAndroid.databinding.ActivityMessageBoxBinding
import com.harmony.harmonyAndroid.preference.SharedManager
import com.harmony.harmonyAndroid.viewmodel.MessageBoxViewModel
import java.sql.Timestamp

class MessageBoxActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMessageBoxBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, MessageBoxViewModel.Factory(application))[MessageBoxViewModel::class.java] }

    lateinit var messageList: MutableList<Message>
    lateinit var messageRecyclerViewAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setUpObserver()
        getAllMessage()

        binding.inputBtn.setOnClickListener {
            insertMessage()
        }
    }

    private fun setUpObserver() {
        viewModel.isGetMessageByReceiverIDComplete.observe(this) {
            messageList = it.toMutableList()
            setUpRecyclerView()
        }

        viewModel.isDeleteMessageComplete.observe(this) {
            val position = messageList.indexOf(it)
            messageList.removeAt(position)
            messageRecyclerViewAdapter.notifyItemRemoved(position)
            messageRecyclerViewAdapter.notifyItemChanged(position)
        }

        viewModel.isDeleteMessageByIDComplete.observe(this) {
            val position = messageList.indexOf(it)
            messageList.removeAt(position)
        }

        viewModel.isInsertMessageComplete.observe(this) {
                id ->
            Log.d("insertComplete::", "memo id is $id")
            messageList.add(Message(id, GlobalApplication.prefs.getUserData().id.toString(), GlobalApplication.prefs.getUserData().id.toString(), binding.titleInput.toString(), binding.contentsInput.toString(), Timestamp(System.currentTimeMillis()).toString()))
            binding.titleInput = ""
            binding.contentsInput = ""
            messageRecyclerViewAdapter.notifyItemInserted(messageList.size-1)
        }
    }

    private fun insertMessage() {
        viewModel.insertMessage(message = Message(0, GlobalApplication.prefs.getUserData().id.toString(), GlobalApplication.prefs.getUserData().id.toString(), binding.titleInput.toString(), binding.contentsInput.toString(), Timestamp(System.currentTimeMillis()).toString()))
    }

    private fun getAllMessage() {
        viewModel.getMessageByReceiverID(GlobalApplication.prefs.getUserData().id.toString())
    }

    private fun setUpRecyclerView() {
        messageRecyclerViewAdapter = MessageAdapter(baseContext, messageList, viewModel)
        binding.messageRecyclerView.adapter = messageRecyclerViewAdapter
        binding.messageRecyclerView.layoutManager = LinearLayoutManager(baseContext)
    }
}