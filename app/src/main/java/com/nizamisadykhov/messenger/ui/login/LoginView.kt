package com.nizamisadykhov.messenger.ui.login

import com.nizamisadykhov.messenger.ui.auth.AuthView
import com.nizamisadykhov.messenger.ui.base.BaseView

interface LoginView : BaseView, AuthView {
    fun showProgress()
    fun hideProgress()
    fun setUsernameError()
    fun setPasswordError()
    fun navigateToSignUp()
    fun navigateToHome()
}