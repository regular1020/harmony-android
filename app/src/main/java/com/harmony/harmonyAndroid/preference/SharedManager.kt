package com.harmony.harmonyAndroid.preference

import android.content.Context
import android.content.SharedPreferences
import com.harmony.harmonyAndroid.data.UserData
import com.harmony.harmonyAndroid.preference.PreferenceHelper.set
import com.harmony.harmonyAndroid.preference.PreferenceHelper.get

class SharedManager(context: Context) {
    private val prefs: SharedPreferences = PreferenceHelper.defaultPrefs(context)

    fun saveUserData(userData: UserData) {
        prefs["id"] = userData.id
        prefs["phone"] = userData.phone
    }

    fun getUserData(): UserData {
        return UserData().apply {
            id = prefs["id", ""]
            phone = prefs["phone", ""]
        }
    }
}