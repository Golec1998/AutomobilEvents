package com.example.projektpam.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favEvents")
data class FavEventsData (
    @PrimaryKey(autoGenerate = false)
    val id : Int
)