package com.harmony.harmonyAndroid.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.harmony.harmonyAndroid.MainActivity
import com.harmony.harmonyAndroid.R

class HarmonyFirebaseMessagingService : FirebaseMessagingService() {
    private val tag = "FCM_MESSAGE"
    private val channelName = "Harmony"
    private val channelDescription = "Harmony Message Notification"
    private val channelID = "MessageReceive"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(tag, "token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]

        Log.d(tag, "onMessageReceived() - title : $title")
        Log.d(tag, "onMessageReceived() - message : $message")

        sendNotification(remoteMessage.notification!!.title, remoteMessage.notification!!.body)
    }

    private fun sendNotification(title: String?, message: String?) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelID,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = channelDescription
            notificationManager.createNotificationChannel(channel)
        }

        NotificationManagerCompat.from(this).notify((System.currentTimeMillis()/100).toInt(), createNotification(title, message))
    }


    private fun createNotification(title: String?, message: String?): Notification
    {

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(this, (System.currentTimeMillis()/100).toInt(), intent, FLAG_UPDATE_CURRENT)

        val notificationBuilder = NotificationCompat.Builder(this, channelID)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        return notificationBuilder.build()
    }
}