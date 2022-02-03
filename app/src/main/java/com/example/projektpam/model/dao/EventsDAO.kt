package com.example.projektpam.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import model.EventsJSON

@Dao
interface EventsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventsJSON(json : EventsJSON)

    @Query("SELECT JSON FROM eventsJSON WHERE id LIKE 1")
    fun readEventsJSON() : String

}