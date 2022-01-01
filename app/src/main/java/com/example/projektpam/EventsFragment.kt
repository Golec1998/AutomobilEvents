package com.example.projektpam

import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.EventsData
import org.json.JSONArray
import org.json.JSONTokener
import java.net.HttpURLConnection
import java.net.URL

class EventsFragment : Fragment() {

    var adapter : RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)

        val eventsRecyclerView = view.findViewById<RecyclerView>(R.id.eventsList)
        eventsRecyclerView.layoutManager = LinearLayoutManager(activity)

        var events : MutableSet<EventsData> = mutableSetOf(EventsData())
        adapter = EventsRecyclerAdapter(events)
        eventsRecyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            var json = URL("https://beckertrans.pl/automobilevents_api/api/event/read.php").readText()

            val jsonArray = JSONTokener(json).nextValue() as JSONArray

            var i = 0
            while (i < jsonArray.length()) {
                val id = jsonArray.getJSONObject(i).getString("id")
                val name = jsonArray.getJSONObject(i).getString("name")
                val description = jsonArray.getJSONObject(i).getString("description")
                val image = jsonArray.getJSONObject(i).getString("image")
                val startDate = jsonArray.getJSONObject(i).getString("start_date")
                val endDate = jsonArray.getJSONObject(i).getString("end_date")

                events.add(EventsData(description, endDate, id, image, name, startDate))
                d("test", events.last().name)
                i++
            }
        }

        return view
    }

    companion object {
        fun newInstance() {}
    }

    fun removeEmptyEvents(events: MutableSet<EventsData>) {
        events.removeIf { it.id == "" }
    }
}
