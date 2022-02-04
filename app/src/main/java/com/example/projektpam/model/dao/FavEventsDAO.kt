package com.example.projektpam.model.dao

import androidx.room.*
import com.example.projektpam.model.entities.FavEventsData

@Dao
interface FavEventsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavEvent(favEvent : FavEventsData)

    @Delete
    suspend fun deleteFavEvent(favEvent : FavEventsData)

    @Query("SELECT id FROM favEvents")
    fun readFavEvents() : MutableList<Int>

}