package com.example.projektpam.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projektpam.model.appDatabase
import com.example.projektpam.model.repository.EventsRepository
import kotlinx.coroutines.*
import model.EventsData

class EventsViewModel(application : Application) : AndroidViewModel(application) {

    private var tempEvents = ArrayList<EventsData>()
    var events = MutableLiveData<List<EventsData>>()
    private val eventsDAO = appDatabase.getDatabase(application).eventsDAO()
    private val repository : EventsRepository = EventsRepository(eventsDAO)

    var favouriteEvents = mutableListOf<String>()

    fun getEvents(con : Boolean) {
        runBlocking {
            val pending = viewModelScope.launch(Dispatchers.IO) {
                tempEvents = repository.getEvents(con)
            }

            pending.join()
            events.value = tempEvents
        }
    }

    fun updateFavEvents(eventId : String) {
        if (eventId in favouriteEvents)
            favouriteEvents.removeAt(favouriteEvents.indexOf(eventId))
        else
            favouriteEvents.add(eventId)
    }

}