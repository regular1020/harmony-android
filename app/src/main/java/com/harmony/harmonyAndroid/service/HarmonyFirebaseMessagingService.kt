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
import com.harmony.harmonyAndroid.data.Message
import com.harmony.harmonyAndroid.data.MessageAddObject
import com.harmony.harmonyAndroid.database.GlobalApplication
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HarmonyFirebaseMessagingService : FirebaseMessagingService() {
    private val tag = "FCM_MESSAGE"
    private val channelName = "Harmony"
    private val channelDescription = "Harmony Message Notification"
    private val channelID = "MessageReceive"
    private val messageDBInstance = GlobalApplication.appDataBaseInstance.messageDao()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(tag, "token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data["title"]
            val contents = remoteMessage.data["contents"]
            val timestamp = remoteMessage.data["timestamp"]
            val sender_id = remoteMessage.data["sender_id"]

            var msg = Message(id = 0, sender_id = sender_id!!, content = contents!!, title = title!!, timestamp = timestamp!!, receiver_id = "test01")

            GlobalScope.launch {
                messageDBInstance.insertMessage(msg)
                MessageAddObject.messageAddLiveData.postValue(msg)
            }

        }

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

        return notificationBuilder.setSmallIcon(R.drawable.ic_baseline_delete_24).build()
    }
}