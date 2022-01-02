package com.example.projektpam

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
        val eventsSwipe = view.findViewById<SwipeRefreshLayout>(R.id.events_swipe_to_refresh)

        var events : MutableSet<EventsData> = mutableSetOf()
        adapter = EventsRecyclerAdapter(events)
        eventsRecyclerView.adapter = adapter
        getEvents(events, eventsRecyclerView)
        refreshEvents(eventsSwipe, events, eventsRecyclerView)

        return view
    }

    companion object {
        fun newInstance() {}
    }

    fun removeEmptyEvents(events: MutableSet<EventsData>) {
        events.removeAll { true }
    }

    private fun refreshEvents(eventsSwipe : SwipeRefreshLayout, events : MutableSet<EventsData>, eventsRecyclerView : RecyclerView) {
        eventsSwipe.isEnabled = true
        eventsSwipe.setOnRefreshListener {
            getEvents(events, eventsRecyclerView)
            eventsSwipe.isRefreshing = false
        }
    }

    private fun getEvents(events : MutableSet<EventsData>, eventsRecyclerView : RecyclerView) {
        lifecycleScope.launch(Dispatchers.IO) {
            var json = ""
            val isConnected : Boolean = isNetworkAvailable()
            if (isConnected)
                try {
                    json = URL("https://beckertrans.pl/automobilevents_api/api/event/read.php").readText()
                } catch (e : Exception) {}

            if (json != "") {
                val jsonArray = JSONTokener(json).nextValue() as JSONArray
                    removeEmptyEvents(events)
                    var i = 0
                    while (i < jsonArray.length()) {
                        val id = jsonArray.getJSONObject(i).getString("id")
                        val name = jsonArray.getJSONObject(i).getString("name")
                        val description = jsonArray.getJSONObject(i).getString("description")
                        val image = jsonArray.getJSONObject(i).getString("image")
                        val startDate = jsonArray.getJSONObject(i).getString("start_date")
                        val endDate = jsonArray.getJSONObject(i).getString("end_date")

                        events.add(EventsData(description, endDate, id, image, name, startDate))
                        i++
                }
                if(activity != null) {
                    requireActivity().runOnUiThread {
                        eventsRecyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            }
            else
                if(activity != null) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(context, "Nie można odświeżyć", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun isNetworkAvailable() : Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}
