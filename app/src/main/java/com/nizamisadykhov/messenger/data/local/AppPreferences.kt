package com.nizamisadykhov.messenger.data.local

import android.content.Context
import android.content.SharedPreferences
import com.nizamisadykhov.messenger.data.vo.UserVO

class AppPreferences private constructor() {
    private lateinit var preferences: SharedPreferences

    val accessToken: String? get() = preferences.getString(ACCESS_TOKEN, null)

    fun storeAccessToken(accessToken: String) {
        preferences.edit().putString(ACCESS_TOKEN, accessToken).apply()
    }

    val userDetails: UserVO
        get() = UserVO(
            id = preferences.getLong(ID, 0),
            username = preferences.getString(USERNAME, null).orEmpty(),
            phoneNumber = preferences.getString(PHONE_NUMBER, null).orEmpty(),
            status = preferences.getString(STATUS, null).orEmpty(),
            createdAt = preferences.getString(CREATED_AT, null).orEmpty()
        )


    fun storeUserDetails(user: UserVO) {
       with(preferences.edit()) {
           putLong(ID, user.id).apply()
           putString(USERNAME, user.username)
           putString(PHONE_NUMBER, user.phoneNumber)
           putString(STATUS, user.status)
           putString(CREATED_AT, user.createdAt)
       }
    }

    fun clear() {
        with(preferences.edit()) {
            clear()
            apply()
        }
    }

    companion object {
        private const val PREFERENCE_FILE_NAME = "APP_PREFERENCES"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val ID = "ID"
        private const val USERNAME = "USERNAME"
        private const val PHONE_NUMBER = "PHONE_NUMBER"
        private const val STATUS = "STATUS"
        private const val CREATED_AT = "CREATED_AT"

        fun create(context: Context): AppPreferences {
            val appPreferences = AppPreferences()
            appPreferences.preferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, 0)
            return appPreferences
        }
    }
}