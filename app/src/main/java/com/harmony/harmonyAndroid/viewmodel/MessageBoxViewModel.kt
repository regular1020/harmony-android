package com.harmony.harmonyAndroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.harmony.harmonyAndroid.data.Message
import com.harmony.harmonyAndroid.preference.SharedManager
import com.harmony.harmonyAndroid.repository.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageBoxViewModel(private val repository: MessageRepository): ViewModel() {
    val isInsertMessageComplete = MutableLiveData<Long>()
    val isDeleteMessageComplete = MutableLiveData<Message>()
    val isDeleteMessageByIDComplete = MutableLiveData<Message>()
    val isGetMessageByReceiverIDComplete = MutableLiveData<List<Message>>()

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MessageBoxViewModel(MessageRepository.getInstance(application)!!) as T
        }
    }

    fun insertMessage(message: Message) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertMessage(message).let {
                id -> isInsertMessageComplete.postValue(id)
            }
        }
    }

    fun deleteMessage(message: Message) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteMessage(message).let {
                isDeleteMessageComplete.postValue(message)
            }
        }
    }

    fun deleteMessageByID(message: Message) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteMessageByID(message.id).let {
                isDeleteMessageByIDComplete.postValue(message)
            }
        }
    }

    fun getMessageByReceiverID(receiver_id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getMessageByReceiverID(receiver_id).let {
                isGetMessageByReceiverIDComplete.postValue(it)
            }
        }
    }
}