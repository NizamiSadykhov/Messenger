package com.nizamisadykhov.messenger.ui.chat

import com.nizamisadykhov.messenger.data.vo.ConversationVO

interface ChatInteractor {

    interface OnMessageSendFinishedListener {
        fun onSendSuccess()
        fun onSendError()
    }

    interface OnMessageLoadFinishedListener {
        fun onLoadSuccess(conversationVO: ConversationVO)
        fun onLoadError()
    }

    fun sendMessage(recipientId: Long, message: String, listener: OnMessageSendFinishedListener)
    fun loadMessages(conversationId: Long, listener: OnMessageLoadFinishedListener)
}