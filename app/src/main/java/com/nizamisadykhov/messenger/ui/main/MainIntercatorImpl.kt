package com.nizamisadykhov.messenger.ui.main

import android.annotation.SuppressLint
import android.content.Context
import com.nizamisadykhov.messenger.data.local.AppPreferences
import com.nizamisadykhov.messenger.data.remote.repository.ConversationRepository
import com.nizamisadykhov.messenger.data.remote.repository.ConversationRepositoryImpl
import com.nizamisadykhov.messenger.data.remote.repository.UserRepository
import com.nizamisadykhov.messenger.data.remote.repository.UserRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainIntercatorImpl(val context: Context) : MainInteractor {

    private val userRepository: UserRepository = UserRepositoryImpl(context)
    private val conversationRepository: ConversationRepository = ConversationRepositoryImpl(context)

    @SuppressLint("CheckResult")
    override fun loadContacts(listener: MainInteractor.OnContactsLoadFinishedListener) {
        userRepository.all()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                listener.onContactsLoadSuccess(res)
            }, { error ->
                listener.onContactsLoadError()
                error.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    override fun loadConversations(listener: MainInteractor.InConversationsLoadFinishedListener) {
        conversationRepository.all()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                listener.onConversationsLoadSuccess(res)
            }, { error ->
                listener.onConversationsLoadError()
                error.printStackTrace()
            })
    }

    override fun logout(listener: MainInteractor.OnLogoutFinishedListener) {
        val preferences: AppPreferences = AppPreferences.create(context)
        preferences.clear()
        listener.onLogoutSuccess()
    }
}
