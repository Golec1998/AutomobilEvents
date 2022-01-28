package com.example.projektpam.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projektpam.model.repository.EventsRepository
import kotlinx.coroutines.*
import model.EventsData

class EventsViewModel(application : Application) : AndroidViewModel(application) {

    private var tempEvents = ArrayList<EventsData>()
    var events = MutableLiveData<List<EventsData>>()
    private val repository : EventsRepository = EventsRepository()

    fun getEvents(con : Boolean) : Boolean {
        runBlocking {
            val pending = viewModelScope.launch(Dispatchers.IO) {
                tempEvents = repository.getEvents(con)
            }

            pending.join()
            events.value = tempEvents
        }

        return tempEvents != ArrayList<EventsData>()
    }

}