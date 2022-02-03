package com.example.projektpam.fragments.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projektpam.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_event.view.*

class EventFragment : Fragment() {

    private val args by navArgs<EventFragmentArgs>()
    val picasso = Picasso.get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        view.backToEventsButton.setOnClickListener { findNavController().navigate(R.id.action_eventFragment_to_eventsFragment) }
        view.eventName.text = args.currentEvent.name
        view.eventDescription.text = args.currentEvent.description
        picasso
            .load("https://beckertrans.pl/automobilevents_api/images/" + args.currentEvent.image + ".jpg")
            .into(view.eventImage)

        return view
    }

}