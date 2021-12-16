package com.nizamisadykhov.messenger.ui.signup

import com.nizamisadykhov.messenger.ui.auth.AuthView
import com.nizamisadykhov.messenger.ui.base.BaseView

interface SignUpView: BaseView, AuthView {
    fun showProgress()
    fun showSignUpError()
    fun hideProgress()
    fun setUsernameError()
    fun setPhoneNumberError()
    fun setPasswordError()
    fun navigateToHome()
}