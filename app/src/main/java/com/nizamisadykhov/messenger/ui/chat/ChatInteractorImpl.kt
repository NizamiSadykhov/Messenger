package com.nizamisadykhov.messenger.ui.chat

import android.annotation.SuppressLint
import android.content.Context
import com.nizamisadykhov.messenger.data.local.AppPreferences
import com.nizamisadykhov.messenger.data.remote.repository.ConversationRepository
import com.nizamisadykhov.messenger.data.remote.repository.ConversationRepositoryImpl
import com.nizamisadykhov.messenger.data.remote.request.MessageRequestObject
import com.nizamisadykhov.messenger.service.MessengerApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChatInteractorImpl(context: Context) : ChatInteractor {

    private val preferences: AppPreferences = AppPreferences.create(context)
    private val service: MessengerApiService = MessengerApiService.getInstance()
    private val conversationsRepository: ConversationRepository =
        ConversationRepositoryImpl(context)

    @SuppressLint("CheckResult")
    override fun loadMessages(
        conversationId: Long,
        listener: ChatInteractor.OnMessageLoadFinishedListener
    ) {
        conversationsRepository.findConversationById(conversationId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ res ->
                listener.onLoadSuccess(res)
            }, { error ->
                listener.onLoadError()
                error.printStackTrace()
            })
    }

    @SuppressLint("CheckResult")
    override fun sendMessage(
        recipientId: Long,
        message: String,
        listener: ChatInteractor.OnMessageSendFinishedListener
    ) {
        val requestObject = MessageRequestObject(recipientId, message)
        val accessToken = preferences.accessToken as String

        service.createMessage(requestObject, accessToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                listener.onSendSuccess()
            }, { error ->
                listener.onSendError()
                error.printStackTrace()
            })
    }
}