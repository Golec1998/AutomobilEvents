package com.example.projektpam.fragments.events

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.projektpam.fragments.adapters.EventsRecyclerAdapter
import com.example.projektpam.R
import com.example.projektpam.viewModel.EventsViewModel
import model.EventsData

class EventsFragment : Fragment() {

    private lateinit var eventsViewModel : EventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)

        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        val eventsRecyclerView = view.findViewById<RecyclerView>(R.id.eventsList)
        eventsRecyclerView.layoutManager = LinearLayoutManager(activity)
        val eventsSwipe = view.findViewById<SwipeRefreshLayout>(R.id.events_swipe_to_refresh)

        var events : MutableSet<EventsData> = mutableSetOf()
        val adapter = EventsRecyclerAdapter(eventsViewModel)
        eventsRecyclerView.adapter = adapter

        eventsViewModel.events.observe(viewLifecycleOwner, { events ->
            adapter.setData(events)
        })

        refreshEvents()
        swipeRefreshEvents(eventsSwipe, eventsRecyclerView)

        return view
    }

    companion object {
        fun newInstance() {}
    }

    private fun refreshEvents() {
        val con = isNetworkAvailable()
        eventsViewModel.getEvents(con)
        if(!con)
            Toast.makeText(context, "Brak połączenia", Toast.LENGTH_SHORT).show()
    }

    private fun swipeRefreshEvents(eventsSwipe : SwipeRefreshLayout, eventsRecyclerView : RecyclerView) {
        eventsSwipe.isEnabled = true
        eventsSwipe.setOnRefreshListener {
            refreshEvents()
            eventsSwipe.isRefreshing = false
        }
    }

    private fun isNetworkAvailable() : Boolean {
        val connectivityManager = context?.getSystemService(CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}

