package com.harmony.harmonyAndroid.repository

import android.app.Application
import com.google.gson.JsonObject
import com.harmony.harmonyAndroid.data.Message
import com.harmony.harmonyAndroid.data.ModelMessageComponent
import com.harmony.harmonyAndroid.database.GlobalApplication
import com.harmony.harmonyAndroid.network.MessageObject
import retrofit2.Response

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
    suspend fun getMessageByReceiverID(receiver_id: String) = messageDBInstance.getMessageByReceiverID(receiver_id)

    suspend fun sendMessage(modelMessageComponent: ModelMessageComponent): Response<JsonObject> {
        return MessageObject.getMessageSendService.postMessage(
            hashMapOf(
                "sender_id" to modelMessageComponent.sender_id,
                "recv_phone_number" to modelMessageComponent.receiver_phone_number,
                "title" to modelMessageComponent.title,
                "contents" to modelMessageComponent.contents,
                "timestamp" to modelMessageComponent.timestamp
            )
        )
    }
}