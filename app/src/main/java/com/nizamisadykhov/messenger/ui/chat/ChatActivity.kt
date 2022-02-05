package com.nizamisadykhov.messenger.ui.chat

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nizamisadykhov.messenger.R
import com.nizamisadykhov.messenger.data.local.AppPreferences
import com.nizamisadykhov.messenger.utils.message.Message
import com.nizamisadykhov.messenger.utils.showLongToast
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter
import java.util.*

class ChatActivity : AppCompatActivity(), ChatView, MessageInput.InputListener {

    private var recipientId: Long = -1
    private lateinit var messageList: MessagesList
    private lateinit var messageInput: MessageInput
    private lateinit var preferences: AppPreferences
    private lateinit var presenter: ChatPresenter
    private lateinit var messageListAdapter: MessagesListAdapter<Message>

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(RECIPIENT_NAME)

        preferences = AppPreferences.create(this)
        messageListAdapter = MessagesListAdapter(preferences.userDetails.id.toString(), null)
        presenter = ChatPresenterImpl(this)
        bindViews()

        val conversationId = intent.getLongExtra(CONVERSATION_ID, -1)
        recipientId = intent.getLongExtra(RECIPIENT_ID, -1)

        if (conversationId != -1L) {
            presenter.loadMessages(conversationId)
        }
    }

    override fun onSubmit(input: CharSequence?): Boolean {
        // Создание нового объекта и его добавления в начало MessagesListAdapter
        val message = Message(
            authorId = preferences.userDetails.id,
            body = input.toString(),
            createdAt = Date()
        )
        messageListAdapter.addToStart(message, true)

        // Начало сообщения, отсылаемого процудурой с ChatPresenter
        presenter.sendMessage(recipientId, input.toString())
        return true
    }

    override fun showConversationLoadError() {
        val text = getString(R.string.conversation_load_error)
        showLongToast(text)
    }

    override fun showMessageSendError() {
        val text = getString(R.string.message_send_error)
        showLongToast(text)
    }

    override fun getContext(): Context {
        return this
    }

    override fun getMessageListAdapter(): MessagesListAdapter<Message> {
        return messageListAdapter
    }

    override fun bindViews() {
        messageList = findViewById(R.id.messages_list)
        messageInput = findViewById(R.id.message_input)
        messageList.setAdapter(messageListAdapter)
        messageInput.setInputListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        private const val RECIPIENT_NAME = "RECIPIENT_NAME"
        private const val RECIPIENT_ID = "RECIPIENT_ID"
        private const val CONVERSATION_ID = "CONVERSATION_ID"
    }
}