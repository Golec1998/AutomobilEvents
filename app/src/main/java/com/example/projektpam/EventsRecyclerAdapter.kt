package com.example.projektpam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatDrawableManager.get
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import model.EventsData
import org.json.JSONArray
import org.json.JSONTokener
import java.net.URL

class EventsRecyclerAdapter(var events : MutableSet<EventsData>) : RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder>() {

    val picasso = Picasso.get()

    var favouriteEvents = listOf<String>("1", "4")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.events_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: EventsRecyclerAdapter.ViewHolder, position: Int) {
        if (events.elementAt(position).name != "") {
            holder.itemTitle.text = events.elementAt(position).name
            holder.itemDate.text =
                events.elementAt(position).start_date + " - " + events.elementAt(position).end_date

            picasso.load(
                "https://beckertrans.pl/automobilevents_api/images/" + events.elementAt(
                    position
                ).image + ".jpg"
            ).into(holder.itemImage)
            //holder.itemImage.setImageResource(R.drawable.class.getField(events.elementAt(position).eventCoverImage))

            if (events.elementAt(position).id in favouriteEvents)
                holder.itemFavourite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        else {
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        }
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