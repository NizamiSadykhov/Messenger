package com.nizamisadykhov.messenger.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nizamisadykhov.messenger.R
import com.nizamisadykhov.messenger.ui.login.LoginActivity
import com.nizamisadykhov.messenger.ui.main.contacts.ContactsFragment
import com.nizamisadykhov.messenger.ui.main.conversation.ConversationFragment
import com.nizamisadykhov.messenger.ui.settings.SettingsActivity
import com.nizamisadykhov.messenger.utils.showLongToast

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var llContainer: LinearLayout
    private lateinit var presenter: MainPresenter

    private val contactsFragment = ContactsFragment()
    private val conversationFragment = ConversationFragment()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenterImpl(this)
        conversationFragment.setActivity(this)
        contactsFragment.setActivity(this)
        bindViews()
        showContactsScreen()
    }

    override fun bindViews() {
        llContainer = findViewById(R.id.ll_container)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun showConversationLoadError() {
        val text = getString(R.string.conversations_load_error)
        showLongToast(text)
    }

    override fun showContactsLoadError() {
        val text = getString(R.string.contacts_load_error)
        showLongToast(text)
    }

    override fun showConversationsScreen() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.ll_container, conversationFragment)

        presenter.loadConversation()
        supportActionBar?.title = getString(R.string.title_messenger)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun showContactsScreen() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.ll_container, contactsFragment)

        presenter.loadContacts()
        supportActionBar?.title = getString(R.string.title_contacts)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showNoConversations() {
        val text = getString(R.string.no_conversations)
        showLongToast(text)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> showConversationsScreen()
            R.id.action_setting -> navigateToSetting()
            R.id.action_logout -> presenter.executeLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getContext(): Context {
        return this
    }

    override fun getContactsFragment(): ContactsFragment {
        return contactsFragment
    }

    override fun getConversationsFragment(): ConversationFragment {
        return conversationFragment
    }

    override fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun navigateToSetting() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}