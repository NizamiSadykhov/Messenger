package com.nizamisadykhov.messenger.ui.main.contacts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nizamisadykhov.messenger.R
import com.nizamisadykhov.messenger.data.vo.UserVO
import com.nizamisadykhov.messenger.ui.chat.ChatActivity
import com.nizamisadykhov.messenger.ui.chat.ChatView

class ContactsAdapter(
    private val context: Context,
    private val dataSet: List<UserVO>
) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>(), ChatView.ChatAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_contacts, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holderContact: ContactViewHolder, position: Int) {
        val item = dataSet[position]
        holderContact.bind(item)
        holderContact.view.setOnClickListener {
            navigateToChat(item.username, item.id)
        }
    }

    override fun navigateToChat(recipientName: String, recipientId: Long, conversationId: Long?) {
        val intent = Intent(context, ChatActivity::class.java)
        intent.putExtra(RECIPIENT_ID, recipientId)
        intent.putExtra(RECIPIENT_NAME, recipientId)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ContactViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val tvUsername: TextView = view.findViewById(R.id.tv_username)
        private val tvPhone: TextView = view.findViewById(R.id.tv_phone)
        private val tvStatus: TextView = view.findViewById(R.id.tv_status)

        fun bind(item: UserVO) {
            tvUsername.text = item.username
            tvPhone.text = item.phoneNumber
            tvStatus.text = item.status
        }

    }

    companion object {
        private const val RECIPIENT_ID = "RECIPIENT_ID"
        private const val RECIPIENT_NAME = "RECIPIENT_NAME"
    }
}