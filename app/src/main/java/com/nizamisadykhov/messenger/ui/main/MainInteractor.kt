package com.nizamisadykhov.messenger.ui.main

import com.nizamisadykhov.messenger.data.vo.ConversationListVO
import com.nizamisadykhov.messenger.data.vo.UserListVO

interface MainInteractor {

    interface InConversationsLoadFinishedListener {
        fun onConversationsLoadSuccess(conversationListVO: ConversationListVO)
        fun onConversationsLoadError()
    }

    interface OnContactsLoadFinishedListener {
        fun onContactsLoadSuccess(userListVO: UserListVO)
        fun onContactsLoadError()
    }

    interface OnLogoutFinishedListener {
        fun onLogoutSuccess()
    }

    fun loadContacts(listener: OnContactsLoadFinishedListener)
    fun loadConversations(listener: InConversationsLoadFinishedListener)
    fun logout(listener: OnLogoutFinishedListener)
}