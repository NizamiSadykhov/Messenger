package com.nizamisadykhov.messenger.ui.signup

import com.nizamisadykhov.messenger.data.local.AppPreferences
import com.nizamisadykhov.messenger.ui.auth.AuthInteractor

class SignUpPresenterImpl(
    private val view: SignUpView
) : SignUpPresenter,
    SignUpInteractor.OnSignUpFinishedListener,
    AuthInteractor.OnAuthFinishedListener {

    private val intercator: SignUpInteractor = SignUpInteractorImpl()
    override var preferences: AppPreferences = AppPreferences.create(view.getContext())

    override fun onSuccess() {
        intercator.getAuthorization(this)
    }

    override fun onError() {
        view.hideProgress()
        view.showSignUpError()
    }

    override fun onUsernameError() {
        view.hideProgress()
        view.setUsernameError()
    }

    override fun onPasswordError() {
        view.hideProgress()
        view.setPasswordError()
    }

    override fun onPhoneNumberError() {
        view.hideProgress()
        view.setPhoneNumberError()
    }

    override fun executeSignUp(username: String, phoneNumber: String, password: String) {
        view.showProgress()
        intercator.signUp(username, phoneNumber, password, this)
    }

    override fun onAuthSuccess() {
        intercator.persistAccessToken(preferences)
        intercator.persistUserDetails(preferences)
        view.hideProgress()
        view.navigateToHome()
    }

    override fun onAuthError() {
        view.hideProgress()
        view.showAuthError()
    }
}