package com.example.projektpam.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.projektpam.R
import com.example.projektpam.fragments.events.EventsFragment
import com.example.projektpam.fragments.events.EventsFragmentDirections
import com.example.projektpam.fragments.events.EventsViewFragment
import com.example.projektpam.viewModel.EventsViewModel
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.events_list_item.view.*
import model.EventsData

class EventsRecyclerAdapter(private val eventsViewModel : EventsViewModel) : RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder>() {

    val picasso = Picasso.get()
    private var events = emptyList<EventsData>()

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var itemImage : ShapeableImageView
        var itemTitle : TextView
        var itemDate : TextView
        var itemFavourite : ImageView

        init {
            itemImage = itemView.findViewById(R.id.eventsListItemImage)
            itemTitle = itemView.findViewById(R.id.eventsListItemHeader)
            itemDate = itemView.findViewById(R.id.eventsListItemDate)
            itemFavourite = itemView.findViewById(R.id.eventsListItemFavourite)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.events_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEvent = events.elementAt(position)
        holder.itemTitle.text = currentEvent.name
        holder.itemDate.text = currentEvent.start_date + " - " + currentEvent.end_date

        picasso
            .load("https://beckertrans.pl/automobilevents_api/images/" + currentEvent.image + ".jpg")
            .into(holder.itemImage)

        holder.itemFavourite.setOnClickListener {
            updateFav(currentEvent.id)
        }

        holder.itemView.eventsListItem.setOnClickListener {
            val action = EventsFragmentDirections.actionEventsFragmentToEventFragment(currentEvent)
            holder.itemView.findNavController().navigate(action)
        }

        if (events.elementAt(position).id in eventsViewModel.favouriteEvents)
            holder.itemFavourite.setImageResource(R.drawable.ic_baseline_favorite_24)
        else
            holder.itemFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
    }

    override fun getItemCount(): Int {
        return events.count()
    }

    fun setData(eventList : List<EventsData>) {
        this.events = eventList
    }

    fun updateFav(id : String) {
        eventsViewModel.updateFavEvents(id)
        notifyDataSetChanged()
    }
}