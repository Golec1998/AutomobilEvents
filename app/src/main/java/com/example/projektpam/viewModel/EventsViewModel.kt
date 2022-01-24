package com.example.projektpam.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.projektpam.model.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import model.EventsData
import kotlinx.coroutines.launch

class EventsViewModel(application : Application) : AndroidViewModel(application) {

    private var tempEvents = ArrayList<EventsData>()
    var events = MutableLiveData<List<EventsData>>()
    private val repository : EventsRepository = EventsRepository()

    fun getEvents(con : Boolean) : Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            tempEvents =  repository.getEvents(con)
        }

        if(tempEvents == ArrayList<EventsData>())
            return false
        else {
            //HERE wymusić przypisywanie wartości dopiero jak sie załaduje
            events.value = tempEvents
            return true
        }
    }

}