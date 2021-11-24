package com.example.projektpam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EventsFragment : Fragment() {

    var adapter : RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)

        val eventsRecyclerView = view.findViewById<RecyclerView>(R.id.eventsList)
        eventsRecyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = EventsRecyclerAdapter()
        eventsRecyclerView.adapter = adapter

        return view
    }

    companion object {
        fun newInstance() {}
    }
}