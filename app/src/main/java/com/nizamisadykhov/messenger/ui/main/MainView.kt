package com.nizamisadykhov.messenger.ui.main

import com.nizamisadykhov.messenger.ui.base.BaseView
import com.nizamisadykhov.messenger.ui.main.contacts.ContactsFragment
import com.nizamisadykhov.messenger.ui.main.conversation.ConversationFragment

interface MainView: BaseView {
    fun showConversationLoadError()
    fun showContactsLoadError()
    fun showConversationsScreen()
    fun showContactsScreen()
    fun getContactsFragment(): ContactsFragment
    fun getConversationsFragment(): ConversationFragment
    fun showNoConversations()
    fun navigateToLogin()
    fun navigateToSetting()
}