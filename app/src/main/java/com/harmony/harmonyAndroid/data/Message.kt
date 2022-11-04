package com.harmony.harmonyAndroid.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
class Message(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var receiver_id : String,
    var sender_id : String,
    var title : String,
    var content : String,
    var timestamp: String
)