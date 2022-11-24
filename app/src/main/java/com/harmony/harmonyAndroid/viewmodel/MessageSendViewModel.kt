package com.harmony.harmonyAndroid.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.harmony.harmonyAndroid.data.ModelMessageComponent
import com.harmony.harmonyAndroid.database.GlobalApplication
import com.harmony.harmonyAndroid.repository.MessageRepository
import kotlinx.coroutines.launch

class MessageSendViewModel(private val repository: MessageRepository) : ViewModel() {
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MessageSendViewModel(MessageRepository.getInstance(application)!!) as T
        }
    }

    fun sendMessage(title: String, receiver_phone: String, contents: String) {
        viewModelScope.launch {
            val response = repository.sendMessage(ModelMessageComponent(GlobalApplication.prefs.getUserData().id!!, receiver_phone, title, contents, System.currentTimeMillis().toString()))
            //Todo: 응답에 따른 결과 추가하기
        }
    }
}