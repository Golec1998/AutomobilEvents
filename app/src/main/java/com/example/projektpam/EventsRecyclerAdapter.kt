package com.example.projektpam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class EventsRecyclerAdapter : RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder>() {

    var events = mutableSetOf<EventsData>(
        EventsData("Japfest", "2022-07-29  -  2022-07-31", R.drawable.japfest),
        EventsData("Ultrace", "2022-08-19  -  2022-06-21", R.drawable.ultrace),
        EventsData("Osaka", "2022-06-24  -  2022-06-26", R.drawable.osaka),
        EventsData("Japfest22", "2022-07-29  -  2022-07-31", R.drawable.japfest),
        EventsData("Ultrace22", "2022-08-19  -  2022-06-21", R.drawable.ultrace),
        EventsData("Osaka22", "2022-06-24  -  2022-06-26", R.drawable.osaka),
        EventsData("Japfest23", "2022-07-29  -  2022-07-31", R.drawable.japfest),
        EventsData("Ultrace23", "2022-08-19  -  2022-06-21", R.drawable.ultrace),
        EventsData("Osaka23", "2022-06-24  -  2022-06-26", R.drawable.osaka)
    )

    var favouriteEvents = mutableSetOf<EventsData>(
        EventsData("Japfest", "2022-07-29  -  2022-07-31", R.drawable.japfest),
        EventsData("Japfest22", "2022-07-29  -  2022-07-31", R.drawable.japfest),
        EventsData("Osaka22", "2022-06-24  -  2022-06-26", R.drawable.osaka),
        EventsData("Ultrace23", "2022-08-19  -  2022-06-21", R.drawable.ultrace)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.events_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: EventsRecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = events.elementAt(position).eventName
        holder.itemDate.text = events.elementAt(position).eventDate
        holder.itemImage.setImageResource(events.elementAt(position).eventCoverImage)

        if(events.elementAt(position) in favouriteEvents)
            holder.itemFavourite.setImageResource(R.drawable.ic_baseline_favorite_24)
    }

    override fun getItemCount(): Int {
        return events.count()
    }

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
}