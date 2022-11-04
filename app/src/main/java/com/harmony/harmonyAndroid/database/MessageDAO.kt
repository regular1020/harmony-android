package com.harmony.harmonyAndroid.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.harmony.harmonyAndroid.data.Message

@Dao
interface MessageDAO {
    @Insert
    suspend fun insertMessage(message: Message): Long

    @Delete
    suspend fun deleteMessage(message: Message)

    @Query("DELETE FROM Message Where id = :id")
    suspend fun deleteMessageByID(id: Long)

    @Query("SELECT * FROM Message Where receiver_id = :receiver_id")
    suspend fun getMessageByReceiverID(receiver_id: String): List<Message>
}