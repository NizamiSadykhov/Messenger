package com.nizamisadykhov.messenger.ui.login

import com.nizamisadykhov.messenger.data.local.AppPreferences
import com.nizamisadykhov.messenger.ui.auth.AuthInteractor

interface LoginInteractor: AuthInteractor {
    interface OnDetailsRetrievalFinishedListener{
        fun onDetailsRetrievalSuccess()
        fun onDetailsRetrievalError()
    }

    fun login(username: String, password: String, listener: AuthInteractor.OnAuthFinishedListener)

    fun retrieveDetails(preferences: AppPreferences, listener: OnDetailsRetrievalFinishedListener)
}