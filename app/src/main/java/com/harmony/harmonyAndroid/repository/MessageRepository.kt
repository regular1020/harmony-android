package com.harmony.harmonyAndroid.repository

import android.app.Application
import com.harmony.harmonyAndroid.data.Message
import com.harmony.harmonyAndroid.database.GlobalApplication

class MessageRepository(application: Application) {
    companion object {
        private var instance: MessageRepository? = null

        fun getInstance(application: Application): MessageRepository? {
            if (instance == null) instance = MessageRepository(application)
            return instance
        }
    }
    private val messageDBInstance = GlobalApplication.appDataBaseInstance.messageDao()

    suspend fun deleteMessage(message: Message) = messageDBInstance.deleteMessage(message)
    suspend fun deleteMessageByID(id: Long) = messageDBInstance.deleteMessageByID(id)
    suspend fun getMessageByReceiverID(receiver_id: String) = messageDBInstance.getMessageByReceiverID(receiver_id)
}