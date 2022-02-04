package com.example.projektpam.model.repository

import com.example.projektpam.model.dao.EventsDAO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import model.EventsData
import model.EventsJSON
import java.net.URL

class EventsRepository(private val eventsDAO : EventsDAO) {

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

}