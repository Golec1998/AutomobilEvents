package com.example.projektpam.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projektpam.model.AppDatabase
import com.example.projektpam.model.entities.FavEventsData
import com.example.projektpam.model.repository.EventsRepository
import kotlinx.coroutines.*
import model.EventsData

class EventsViewModel(application : Application) : AndroidViewModel(application) {

    private var tempEvents = ArrayList<EventsData>()
    var events = MutableLiveData<List<EventsData>>()
    private val eventsDAO = AppDatabase.getDatabase(application).eventsDAO()
    private val favEventsDAO = AppDatabase.getDatabase(application).favEventsDAO()
    private val repository : EventsRepository = EventsRepository(eventsDAO, favEventsDAO)
    var favouriteEvents = mutableListOf<Int>()

    fun getEvents(con : Boolean) {
        runBlocking {
            val pending = viewModelScope.launch(Dispatchers.IO) {
                tempEvents = repository.getEvents(con)
            }

            pending.join()
            events.value = tempEvents
            updateFavEvents("x")
        }
    }

    fun updateFavEvents(eventId : String) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteEvents = repository.updateFavEvents(eventId)
        }
    }

}