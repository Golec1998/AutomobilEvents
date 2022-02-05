package com.example.projektpam.fragments.events

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projektpam.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_event_notification.view.*
import model.EventsData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventNotificationFragment : Fragment() {

    private val args by navArgs<EventNotificationFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backToEvent(view, args.currentEvent)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event_notification, container, false)

        view.eventNotificationName.text = args.currentEvent.name
        view.eventNotificationDate.text = args.currentEvent.start_date + " - " + args.currentEvent.end_date
        view.eventNotificationTimePicker.setIs24HourView(true)
        view.eventNotificationTimePicker.currentHour = 9
        view.eventNotificationTimePicker.currentMinute = 0
        view.eventNotificationDatePicker.init(
            LocalDate.parse(args.currentEvent.start_date, DateTimeFormatter.ISO_DATE).year,
            LocalDate.parse(args.currentEvent.start_date, DateTimeFormatter.ISO_DATE).monthValue - 1,
            LocalDate.parse(args.currentEvent.start_date, DateTimeFormatter.ISO_DATE).dayOfMonth,
            null
        )

        view.backToEventButton.setOnClickListener { backToEvent(view, args.currentEvent) }
        view.setEventNotificationButton.setOnClickListener { setNotification() }

        return view
    }

    private fun setNotification() {
        TODO("Not yet implemented")
    }

    private fun backToEvent(view: View, event : EventsData) {
        val action = EventNotificationFragmentDirections.actionEventNotificationFragmentToEventFragment(event)
        view.findNavController().navigate(action)
    }

}