package com.harmony.harmonyAndroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.harmony.harmonyAndroid.data.Message
import com.harmony.harmonyAndroid.repository.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageBoxViewModel(private val repository: MessageRepository): ViewModel() {
    val isGetMessageByReceiverIDComplete = MutableLiveData<List<Message>>()

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MessageBoxViewModel(MessageRepository.getInstance(application)!!) as T
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