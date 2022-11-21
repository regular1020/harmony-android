package com.harmony.harmonyAndroid.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.harmony.harmonyAndroid.data.Message

@Database(entities = [Message::class], version = 1)
abstract class MessageDataBase: RoomDatabase() {
    abstract fun messageDao() : MessageDAO
}