package com.example.projektpam.fragments.favourites

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projektpam.R
import com.example.projektpam.model.*
import kotlinx.android.synthetic.main.fragment_favourite_notification.view.*
import model.EventsData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class FavouriteNotificationFragment : Fragment() {

    private val args by navArgs<FavouriteNotificationFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backToFavourite(view)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite_notification, container, false)

        view.favouriteNotificationName.text = args.currentEvent.name
        view.favouriteNotificationDate.text = args.currentEvent.start_date + " - " + args.currentEvent.end_date
        view.favouriteNotificationTimePicker.setIs24HourView(true)
        view.favouriteNotificationTimePicker.currentHour = 9
        view.favouriteNotificationTimePicker.currentMinute = 0
        view.favouriteNotificationDatePicker.init(
            LocalDate.parse(args.currentEvent.start_date, DateTimeFormatter.ISO_DATE).year,
            LocalDate.parse(args.currentEvent.start_date, DateTimeFormatter.ISO_DATE).monthValue - 1,
            LocalDate.parse(args.currentEvent.start_date, DateTimeFormatter.ISO_DATE).dayOfMonth,
            null
        )

        view.backToFavouriteButton.setOnClickListener { backToFavourite(view) }
        view.setFavouriteNotificationButton.setOnClickListener { setNotification(view) }

        createNotificationChannel()

        return view
    }

    private fun backToFavourite(view: View, event : EventsData = args.currentEvent) {
        val action = FavouriteNotificationFragmentDirections.actionFavouriteNotificationFragmentToFavouriteFragment(event)
        view.findNavController().navigate(action)
    }

    private fun createNotificationChannel() {
        val name = "Event notification"
        val description = "A description of channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = description
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun setNotification(view : View) {
        val intent = Intent(context, Notification::class.java)
        val title = view.favouriteNotificationName.text
        val message = "Twoje powiadomienie o evencie " + view.favouriteNotificationName.text
        val time = getTime(view)

        if(time > 0) {
            intent.putExtra(titleExtra, title)
            intent.putExtra(messageExtra, message)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                (0..2147483647).random(),
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
            backToFavourite(view)
        }
        else
            Toast.makeText(context, "Zła data", Toast.LENGTH_SHORT).show()
    }

    private fun getTime(view : View) : Long {
        val minute = view.favouriteNotificationTimePicker.minute
        val hour = view.favouriteNotificationTimePicker.hour

        val day = view.favouriteNotificationDatePicker.dayOfMonth
        val month = view.favouriteNotificationDatePicker.month
        val year = view.favouriteNotificationDatePicker.year

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