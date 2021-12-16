package com.nizamisadykhov.messenger.ui.auth

import com.nizamisadykhov.messenger.data.local.AppPreferences
import com.nizamisadykhov.messenger.data.vo.UserVO

interface AuthInteractor {
    var userDetails: UserVO
    var accessToken: String
    var submittedUsername: String
    var submittedPassword: String


    interface onAuthFinishedListener {
        fun onAuthSuccess()
        fun onAuthError()
        fun onUsernameError()
        fun onPasswordError()
    }

    fun persistAccessToken(preferences: AppPreferences)
    fun persistUserDetails(preferences: AppPreferences)
}