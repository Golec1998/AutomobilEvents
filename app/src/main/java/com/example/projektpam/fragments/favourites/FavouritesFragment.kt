package com.example.projektpam.fragments.favourites

import android.content.Context
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
import com.example.projektpam.R
import com.example.projektpam.fragments.adapters.EventsRecyclerAdapter
import com.example.projektpam.viewModel.EventsViewModel

class FavouritesFragment : Fragment() {

    private lateinit var eventsViewModel : EventsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        val eventsRecyclerView = view.findViewById<RecyclerView>(R.id.favouritesList)
        eventsRecyclerView.layoutManager = LinearLayoutManager(activity)
        val favouritesSwipe = view.findViewById<SwipeRefreshLayout>(R.id.favourites_swipe_to_refresh)

        val adapter = EventsRecyclerAdapter(eventsViewModel, "f")
        eventsRecyclerView.adapter = adapter

        eventsViewModel.favEvents.observe(viewLifecycleOwner, { events -> adapter.setData(events) })

        refreshEvents()
        swipeRefreshEvents(favouritesSwipe)

        return view
    }

    private fun refreshEvents() {
        val con = isNetworkAvailable()
        eventsViewModel.getEvents(con)
        if(!con)
            Toast.makeText(context, "Brak połączenia", Toast.LENGTH_SHORT).show()
    }

    private fun swipeRefreshEvents(eventsSwipe : SwipeRefreshLayout) {
        eventsSwipe.isEnabled = true
        eventsSwipe.setOnRefreshListener {
            refreshEvents()
            eventsSwipe.isRefreshing = false
        }
    }

    private fun isNetworkAvailable() : Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    companion object {
        fun newInstance() {}
    }

}