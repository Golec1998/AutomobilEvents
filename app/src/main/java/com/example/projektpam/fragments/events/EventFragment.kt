package com.example.projektpam.fragments.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projektpam.R
import com.example.projektpam.viewModel.EventsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EventFragment : Fragment() {

    private val args by navArgs<EventFragmentArgs>()
    private lateinit var eventsViewModel : EventsViewModel
    val picasso = Picasso.get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        view.eventItemFavourite.setOnClickListener {
            updateFavouriteIcon(view, args.currentEvent.id)
        }
        view.backToEventsButton.setOnClickListener { findNavController().navigate(R.id.action_eventFragment_to_eventsFragment) }
        view.eventName.text = args.currentEvent.name
        view.eventDescription.text = args.currentEvent.description
        picasso
            .load("https://beckertrans.pl/automobilevents_api/images/" + args.currentEvent.image + ".jpg")
            .into(view.eventImage)

        updateFavouriteIcon(view)

        return view
    }

    private fun updateFavouriteIcon(view : View, id : String = "x") {
        runBlocking {
            val pending = this.launch(Dispatchers.IO) {
                eventsViewModel.updateFavEvents(id)
                Thread.sleep(100)
            }
            pending.join()

            if(args.currentEvent.id.toInt() in eventsViewModel.favouriteEvents)
                view.eventItemFavourite.setImageResource(R.drawable.ic_baseline_favorite_24)
            else
                view.eventItemFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

}