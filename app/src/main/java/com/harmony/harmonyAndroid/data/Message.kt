package com.harmony.harmonyAndroid.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class Message(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var receiver_id : String,
    var sender_id : String,
    var title : String,
    var content : String,
    var timestamp: String
): Parcelable