package com.nizamisadykhov.messenger.ui.main.conversation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.nizamisadykhov.messenger.R
import com.nizamisadykhov.messenger.data.local.AppPreferences
import com.nizamisadykhov.messenger.data.vo.ConversationVO

class ConversationsAdapter(
    private val context: Context,
    private val dataSet: List<ConversationVO>
) : RecyclerView.Adapter<ConversationsAdapter.ViewHolder>(), СhatView.ChatAdapter {

    val preferences: AppPreferences = AppPreferences.create(context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)

        holder.view.setOnClickListener {
            val message = item.messages[0]
            val recipientId: Long = if (message.senderId == preferences.userDetails.id) {
                message.recipientId
            } else {
                message.senderId
            }
            navigateToChat(item.secondPartyUsername, recipientId, item.conversationId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vh_conversation, null, false)

        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun navigateToChat(recipientName: String, recipientId: Long, conversationId: Long?) {
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra(CONVERSATION_ID, conversationId)
        intent.putExtra(RECIPIENT_ID, recipientId)
        intent.putExtra(RECIPIENT_NAME, recipientId)
        context.startActivity(intent)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val tvUsername: TextView = view.findViewById(R.id.tv_username)
        private val tvPreview: TextView = view.findViewById(R.id.tv_preview)

        fun bind(item: ConversationVO) {
            tvUsername.text = item.secondPartyUsername
            tvPreview.text = item.messages[item.messages.size - 1].body
        }
    }

    companion object {
        private const val CONVERSATION_ID = "CONVERSATION_ID"
        private const val RECIPIENT_ID = "RECIPIENT_ID"
        private const val RECIPIENT_NAME = "RECIPIENT_NAME"
    }
}