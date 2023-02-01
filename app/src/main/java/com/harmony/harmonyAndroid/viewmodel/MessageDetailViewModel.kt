package com.harmony.harmonyAndroid.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.harmony.harmonyAndroid.data.Message
import com.harmony.harmonyAndroid.data.MessageDeleteObject
import com.harmony.harmonyAndroid.repository.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageDetailViewModel(private val repository: MessageRepository): ViewModel() {
    val isMessageDeleted = MutableLiveData<Boolean>()

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MessageDetailViewModel(MessageRepository.getInstance(application)!!) as T
        }
    }

    fun deleteMessage(message: Message) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteMessage(message).let {
                isMessageDeleted.postValue(true)
                MessageDeleteObject.messageDeleteLiveData.postValue(message)
            }
        }
    }
}