package com.example.projektpam.model.repository

import androidx.lifecycle.viewModelScope
import com.example.projektpam.model.dao.EventsDAO
import com.example.projektpam.model.dao.FavEventsDAO
import com.example.projektpam.model.entities.FavEventsData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.EventsData
import model.EventsJSON
import java.net.URL

class EventsRepository(private val eventsDAO : EventsDAO, private val favEventsDAO : FavEventsDAO) {

    suspend fun getEvents(con : Boolean) : ArrayList<EventsData> {

        var json = ""
        if (con) {
            try {
                json = URL("https://beckertrans.pl/automobilevents_api/api/event/read.php").readText()
            } catch (e: Exception) {}
            eventsDAO.insertEventsJSON(EventsJSON(1,json))
        }
        else {
            json = eventsDAO.readEventsJSON()
        }

        val gson : ArrayList<EventsData> = Gson().fromJson(json, object : TypeToken<ArrayList<EventsData>>() {}.type)
        return gson
    }

    suspend fun updateFavEvents(eventId : String) : MutableList<Int> {
        if (eventId == "x")
            return favEventsDAO.readFavEvents()

        if (eventId.toInt() in favEventsDAO.readFavEvents())
            favEventsDAO.deleteFavEvent(FavEventsData(eventId.toInt()))
        else
            favEventsDAO.insertFavEvent(FavEventsData(eventId.toInt()))

        return favEventsDAO.readFavEvents()
    }

}