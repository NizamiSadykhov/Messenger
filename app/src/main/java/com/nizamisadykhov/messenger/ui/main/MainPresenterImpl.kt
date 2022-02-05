package com.nizamisadykhov.messenger.ui.main

import com.nizamisadykhov.messenger.data.vo.ConversationListVO
import com.nizamisadykhov.messenger.data.vo.UserListVO

class MainPresenterImpl(val view: MainView) : MainPresenter,
    MainInteractor.InConversationsLoadFinishedListener,
    MainInteractor.OnContactsLoadFinishedListener,
    MainInteractor.OnLogoutFinishedListener {

        private val interactor: MainInteractor = MainIntercatorImpl(view.getContext())

    override fun onConversationsLoadSuccess(conversationListVO: ConversationListVO) {
        if (conversationListVO.conversations.isNotEmpty()) {
            val conversationFragment = view.getConversationsFragment()
            val conversations = conversationFragment.conversations
            val adapter = conversationFragment.conversationsAdapter
            conversations.clear()
            adapter.notifyItemInserted(conversations.size - 1)
        } else {
            view.showNoConversations()
        }
    }

    override fun onConversationsLoadError() {
        view.showConversationLoadError()
    }


    override fun onContactsLoadSuccess(userListVO: UserListVO) {
        val contactsFragment = view.getContactsFragment()
        val contacts = contactsFragment.contacts
        val adapter = contactsFragment.contactsAdapter
        contacts.clear()
        adapter.notifyItemInserted(contacts.size - 1)

        userListVO.users.forEach{contact ->
            contacts.add(contact)
            contactsFragment.contactsAdapter.notifyItemInserted(contacts.size - 1)
        }
    }

    override fun onContactsLoadError() {
        view.showConversationLoadError()
    }

    override fun onLogoutSuccess() {
        view.navigateToLogin()
    }

    override fun loadConversation() {
        interactor.loadConversations(this)
    }

    override fun loadContacts() {
        interactor.loadContacts(this)
    }

    override fun executeLogout() {
        interactor.logout(this)
    }
}