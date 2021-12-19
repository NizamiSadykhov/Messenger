package com.nizamisadykhov.messenger.ui.chat

import android.annotation.SuppressLint
import android.widget.Toast
import com.nizamisadykhov.messenger.data.vo.ConversationVO
import com.nizamisadykhov.messenger.utils.message.Message
import java.text.SimpleDateFormat
import java.util.*

class ChatPresenterImpl(private val view: ChatView) : ChatPresenter,
    ChatInteractor.OnMessageSendFinishedListener,
    ChatInteractor.OnMessageLoadFinishedListener {

    private val interactor: ChatInteractor = ChatInteractorImpl(view.getContext())

    @SuppressLint("SimpleDateFormat")
    override fun onLoadSuccess(conversationVO: ConversationVO) {
        val adapter = view.getMessageListAdapter()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        conversationVO.messages.forEach { message ->
            val msg = Message(
                authorId = message.senderId,
                body = message.body,
                createdAt = dateFormat.parse(message.createdAt.split(".")[0]) as Date
            )
            adapter.addToStart(msg, true)
        }
    }

    override fun onLoadError() {
        view.showConversationLoadError()
    }

    override fun onSendSuccess() {
        Toast.makeText(view.getContext(), "Message send", Toast.LENGTH_LONG).show()
    }

    override fun onSendError() {
        view.showMessageSendError()
    }

    override fun sendMessage(recipientId: Long, message: String) {
        interactor.sendMessage(recipientId, message, this)
    }

    override fun loadMessages(conversationId: Long) {
        interactor.loadMessages(conversationId, this)
    }
}