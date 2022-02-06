package com.example.projektpam.fragments.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projektpam.R
import com.example.projektpam.fragments.adapters.NotificationAdapter
import com.example.projektpam.model.Notification
import com.example.projektpam.viewModel.EventsViewModel
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class NotificationsFragment : Fragment() {

    private lateinit var eventsViewModel : EventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        val notificationsRecyclerView = view.findViewById<RecyclerView>(R.id.notificationsList)
        notificationsRecyclerView.layoutManager = LinearLayoutManager(activity)

        val adapter = NotificationAdapter(eventsViewModel)
        notificationsRecyclerView.adapter = adapter

        eventsViewModel.notifications.observe(viewLifecycleOwner, { notificationsList -> adapter.setData(notificationsList) })

        return view
    }

    companion object {
        fun newInstance() {}
    }
}