package com.example.projektpam.fragments.events

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projektpam.R
import com.example.projektpam.model.*
import com.example.projektpam.model.entities.NotificationsData
import com.example.projektpam.viewModel.EventsViewModel
import kotlinx.android.synthetic.main.fragment_event_notification.view.*
import kotlinx.android.synthetic.main.fragment_favourite_notification.view.*
import model.EventsData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class EventNotificationFragment : Fragment() {

    private val args by navArgs<EventNotificationFragmentArgs>()
    private lateinit var eventsViewModel : EventsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backToEvent(view)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event_notification, container, false)

        eventsViewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

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

        view.backToEventButton.setOnClickListener { backToEvent(view) }
        view.setEventNotificationButton.setOnClickListener { setNotification(view) }

        createNotificationChannel()

        return view
    }

    private fun backToEvent(view: View, event : EventsData = args.currentEvent) {
        val action = EventNotificationFragmentDirections.actionEventNotificationFragmentToEventFragment(event)
        view.findNavController().navigate(action)
    }

    private fun createNotificationChannel() {
        val name = "Event notification"
        val description = "Notification about base event"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = description
        val notificationManager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun setNotification(view : View) {
        val intent = Intent(context, Notification::class.java)
        val notId : Int = (0..2147483647).random()
        val title = view.eventNotificationName.text.toString()
        val message = "Twoje powiadomienie o " + view.eventNotificationName.text
        val time = getTime(view)

        if(time > 0) {
            intent.putExtra(titleExtra, title)
            intent.putExtra(messageExtra, message)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                notId,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )

            Toast.makeText(context, "Zapisano powiadomienie", Toast.LENGTH_SHORT).show()
            var notificationTime = view.eventNotificationDatePicker.year.toString() + "-"
            if (view.eventNotificationDatePicker.month < 9)
                notificationTime += "0"
            notificationTime += (view.eventNotificationDatePicker.month + 1).toString() + "-"
            if (view.eventNotificationDatePicker.dayOfMonth < 10)
                notificationTime += "0"
            notificationTime += view.eventNotificationDatePicker.dayOfMonth.toString() + "   " + view.eventNotificationTimePicker.hour.toString() + ":"
            if (view.eventNotificationTimePicker.minute < 10)
                notificationTime += "0"
            notificationTime += view.eventNotificationTimePicker.minute.toString()

            eventsViewModel.insertNotification(NotificationsData(notId, message, notificationTime))
            backToEvent(view)
        }
        else
            Toast.makeText(context, "Zła data", Toast.LENGTH_SHORT).show()
    }

    private fun getTime(view : View) : Long {
        val minute = view.eventNotificationTimePicker.minute
        val hour = view.eventNotificationTimePicker.hour

        val day = view.eventNotificationDatePicker.dayOfMonth
        val month = view.eventNotificationDatePicker.month
        val year = view.eventNotificationDatePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute, 0)
        val endDate = LocalDate.parse(args.currentEvent.end_date, DateTimeFormatter.ISO_DATE)
        val endCalendar = Calendar.getInstance()
        endCalendar.set(
            endDate.year,
            endDate.monthValue,
            endDate.dayOfMonth,
            23, 59, 59
        )

        return if (checkTime(calendar, endCalendar))
            calendar.timeInMillis
        else
            -1
    }

    private fun checkTime(calendar : Calendar, endCalendar : Calendar) : Boolean {
        return !(calendar.before(Calendar.getInstance()) || calendar.after(endCalendar))
    }

}