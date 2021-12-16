package com.nizamisadykhov.messenger.ui.signup

import com.nizamisadykhov.messenger.data.local.AppPreferences

interface SignUpPresenter {
    var preferences: AppPreferences

    fun executeSignUp(username: String, phoneNumber: String, password: String)
}