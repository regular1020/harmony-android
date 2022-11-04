package com.harmony.harmonyAndroid.database

import android.app.Application
import androidx.room.Room
import com.harmony.harmonyAndroid.data.UserData
import com.harmony.harmonyAndroid.preference.SharedManager

class GlobalApplication: Application() {
    companion object {
        lateinit var appInstance: GlobalApplication
            private set

        lateinit var appDataBaseInstance: MessageDataBase
            private set

        lateinit var prefs: SharedManager
    }

    override fun onCreate() {
        super.onCreate()

        prefs = SharedManager(applicationContext)

        appInstance = this

        appDataBaseInstance = Room.databaseBuilder(
            appInstance.applicationContext,
            MessageDataBase::class.java,
            "message.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }
}