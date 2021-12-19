package com.nizamisadykhov.messenger.ui.main.conversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nizamisadykhov.messenger.R
import com.nizamisadykhov.messenger.data.vo.ConversationVO
import com.nizamisadykhov.messenger.ui.main.MainActivity

class ConversationFragment : Fragment(), View.OnClickListener {
    private lateinit var activity: MainActivity
    private lateinit var rvConversation: RecyclerView
    private lateinit var fabContacts: FloatingActionButton

    var conversations: List<ConversationVO> = listOf()
    lateinit var conversationsAdapter: ConversationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val baseLayout = inflater.inflate(R.layout.fragment_conversation, container, false)

        rvConversation = baseLayout.findViewById(R.id.rv_conversations)
        fabContacts = baseLayout.findViewById(R.id.fab_contact)
        conversationsAdapter = ConversationsAdapter(baseLayout.context, conversations)

        rvConversation.adapter = conversationsAdapter
        rvConversation.layoutManager = LinearLayoutManager(getActivity()?.baseContext)

        fabContacts.setOnClickListener(this)
        return baseLayout
    }


    override fun onClick(view: View) {
        if (view.id == R.id.fab_contact) {
            this.activity.showContactsScreen()
        }
    }

    fun setActivity(activity: MainActivity) {
        this.activity = activity
    }
}