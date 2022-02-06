package com.example.projektpam.viewModel

import android.app.Application
import android.app.Notification
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projektpam.model.AppDatabase
import com.example.projektpam.model.entities.FavEventsData
import com.example.projektpam.model.entities.NotificationsData
import com.example.projektpam.model.repository.EventsRepository
import kotlinx.coroutines.*
import model.EventsData
import java.util.*
import kotlin.collections.ArrayList

class EventsViewModel(application : Application) : AndroidViewModel(application) {

    var events = MutableLiveData<List<EventsData>>()
    var favEvents = MutableLiveData<List<EventsData>>()
    private val eventsDAO = AppDatabase.getDatabase(application).eventsDAO()
    private val favEventsDAO = AppDatabase.getDatabase(application).favEventsDAO()
    private val notificationsDAO = AppDatabase.getDatabase(application).notificationsDAO()
    private val repository : EventsRepository = EventsRepository(eventsDAO, favEventsDAO, notificationsDAO)
    var favouriteEvents = mutableListOf<Int>()
    val notifications = repository.notifications

    fun getEvents(con : Boolean) {
        var tempEvents = ArrayList<EventsData>()
        var tempFavEvents = ArrayList<EventsData>()

        runBlocking {
            val pending = viewModelScope.launch(Dispatchers.IO) {
                tempEvents = repository.getEvents(con)
                favouriteEvents = repository.updateFavEvents("x")
                for(i in tempEvents)
                    if (i.id.toInt() in favouriteEvents)
                        tempFavEvents.add(i)
            }
            pending.join()

            events.value = tempEvents
            favEvents.value = tempFavEvents

            updateFavEvents("x")
        }
    }

    fun updateFavEvents(eventId : String) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteEvents = repository.updateFavEvents(eventId)
        }
    }

    fun insertNotification(notification : NotificationsData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNotification(notification)
        }
    }

    fun deleteNotification(id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNotification(id)
        }
    }

}