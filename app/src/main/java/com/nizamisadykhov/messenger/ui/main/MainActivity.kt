package com.nizamisadykhov.messenger.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nizamisadykhov.messenger.R
import com.nizamisadykhov.messenger.data.vo.ConversationVO
import com.nizamisadykhov.messenger.ui.login.LoginActivity
import com.nizamisadykhov.messenger.ui.main.contacts.ContactsFragment
import com.nizamisadykhov.messenger.ui.main.conversation.ConversationFragment

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
        Toast.makeText(this, "Unable to load conversations. Try again later.", Toast.LENGTH_LONG)
            .show()
    }

    override fun showContactsLoadError() {
        Toast.makeText(this, "Unable to load contacts. Try again later.", Toast.LENGTH_LONG)
            .show()
    }

    override fun showConversationsScreen() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.ll_container, conversationFragment)

        presenter.loadConversation()
        supportActionBar?.title = "Messenger"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun showContactsScreen() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.ll_container, contactsFragment)

        presenter.loadContacts()
        supportActionBar?.title = "Contacts"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showNoConversations() {
        Toast.makeText(this, "You have no active conversations.", Toast.LENGTH_LONG)
            .show()
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
        startActivity(Intent(this, SettingActivity::class.java))
    }
}