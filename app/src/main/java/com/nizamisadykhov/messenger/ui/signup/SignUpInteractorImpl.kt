package com.nizamisadykhov.messenger.ui.signup

import android.annotation.SuppressLint
import android.text.TextUtils
import com.nizamisadykhov.messenger.data.local.AppPreferences
import com.nizamisadykhov.messenger.data.remote.request.LoginRequestObject
import com.nizamisadykhov.messenger.data.remote.request.UserRequestObject
import com.nizamisadykhov.messenger.data.vo.UserVO
import com.nizamisadykhov.messenger.service.AUTHORIZATION
import com.nizamisadykhov.messenger.service.MessengerApiService
import com.nizamisadykhov.messenger.ui.auth.AuthInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignUpInteractorImpl : SignUpInteractor {
    override lateinit var userDetails: UserVO
    override lateinit var accessToken: String
    override lateinit var submittedUsername: String
    override lateinit var submittedPassword: String

    private val service: MessengerApiService = MessengerApiService.getInstance()

    @SuppressLint("CheckResult")
    override fun signUp(
        username: String,
        phoneNumber: String,
        password: String,
        listener: SignUpInteractor.OnSignUpFinishedListener
    ) {
        submittedUsername = username
        submittedPassword = password
        val userRequestObject = UserRequestObject(username, password, phoneNumber)
        when {
            TextUtils.isEmpty(username) -> listener.onUsernameError()
            TextUtils.isEmpty(phoneNumber) -> listener.onPhoneNumberError()
            TextUtils.isEmpty(password) -> listener.onPasswordError()
            else -> {
                service.createUser(userRequestObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ res ->
                        userDetails = res
                        listener.onSuccess()
                    }, { error ->
                        listener.onError()
                        error.printStackTrace()
                    })
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun getAuthorization(listener: AuthInteractor.OnAuthFinishedListener) {
        val userRequestObject = LoginRequestObject(submittedUsername, submittedPassword)
        service.login(userRequestObject)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                accessToken = res.headers()[AUTHORIZATION] as String
                listener.onAuthSuccess()
            }, { error ->
                listener.onAuthError()
                error.printStackTrace()
            })
    }

    override fun persistAccessToken(preferences: AppPreferences) {
        preferences.storeAccessToken(accessToken)
    }

    override fun persistUserDetails(preferences: AppPreferences) {
        preferences.storeUserDetails(userDetails)
    }
}