package com.nizamisadykhov.messenger.ui.main.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nizamisadykhov.messenger.R
import com.nizamisadykhov.messenger.data.vo.UserVO
import com.nizamisadykhov.messenger.ui.main.MainActivity

class ContactsFragment: Fragment() {
    private lateinit var activity: MainActivity
    private lateinit var rvContacts: RecyclerView

    var contacts: ArrayList<UserVO> = ArrayList()
    lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val baseLayout = inflater.inflate(R.layout.fragment_contacts, container, false)
        rvContacts = baseLayout.findViewById(R.id.rv_contacts)
        contactsAdapter = ContactsAdapter(baseLayout.context, contacts)
        rvContacts.adapter = contactsAdapter
        rvContacts.layoutManager = LinearLayoutManager(getActivity()?.baseContext)
        return baseLayout
    }

    fun setActivity(activity: MainActivity){
        this.activity = activity
    }
}