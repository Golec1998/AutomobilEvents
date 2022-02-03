package model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventsData(
    val description: String = "",
    val end_date: String = "",
    val id: String = "",
    val image: String = "",
    val name: String = "",
    val start_date: String = ""
) : Parcelable