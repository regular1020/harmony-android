package com.harmony.harmonyAndroid.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class HarmonyFirebaseMessagingService : FirebaseMessagingService() {
    private val tag = "FCM_MESSAGE"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(tag, "token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.notification != null) {
            val body: String = message.notification!!.body.toString()
            Log.d(tag,"Notification Body: $body")
        }
    }

}