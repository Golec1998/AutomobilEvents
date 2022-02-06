package com.example.projektpam.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notifications")
data class NotificationsData (
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val name : String,
    val date : String
)