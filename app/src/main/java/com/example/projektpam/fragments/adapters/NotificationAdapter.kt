package com.example.projektpam.fragments.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.projektpam.R
import com.example.projektpam.model.Notification
import com.example.projektpam.model.entities.NotificationsData
import com.example.projektpam.viewModel.EventsViewModel
import com.google.android.material.imageview.ShapeableImageView
import java.text.SimpleDateFormat
import java.util.*

class NotificationAdapter(private val eventsViewModel : EventsViewModel) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private var notifications = emptyList<NotificationsData>()
    private val notification = Notification()

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle : TextView
        var itemDate : TextView
        var itemDeleteButton : ImageView
        var itemImage : ShapeableImageView
        var itemBody : ConstraintLayout

        init {
            itemTitle = itemView.findViewById(R.id.notificationListItemHeader)
            itemDate = itemView.findViewById(R.id.notificationListItemDate)
            itemDeleteButton = itemView.findViewById(R.id.notificationListItemDelete)
            itemImage = itemView.findViewById(R.id.notificationListItemImage)
            itemBody = itemView.findViewById(R.id.notificationListItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int) : NotificationAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.notifications_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
        val currentNotification = notifications.elementAt(position)

        holder.itemTitle.text = currentNotification.name
        holder.itemDate.text = currentNotification.date

        holder.itemDeleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(holder.itemBody.context)
            builder.setPositiveButton("Tak") { _, _ ->
                eventsViewModel.deleteNotification(currentNotification.id)
                notification.cancelNotification(currentNotification.id, holder.itemBody.context)
            }
            builder.setNegativeButton("Nie") { _, _ -> }
            builder.setTitle("Usuń powiadomienie")
            builder.setMessage("Czy chcesz usunąć powiadomienie?")
            builder.create().show()
        }

        val currentDate = Calendar.getInstance()
        val checkDate = Calendar.getInstance()
        val d = holder.itemDate.text.toString().split("   ")
        val dd = d[0].split("-")
        val dt = d[1].split(":")
        checkDate.set(
            dd[0].toInt(),
            dd[1].toInt() - 1,
            dd[2].toInt(),
            dt[0].toInt(),
            dt[1].toInt(),
            0)

        if (currentDate.after(checkDate)) {
            eventsViewModel.deleteNotification(currentNotification.id)
        }
    }

    override fun getItemCount(): Int {
        return notifications.count()
    }

    fun setData(notificationList : List<NotificationsData>) {
        this.notifications = notificationList
        notifyDataSetChanged()
    }

}