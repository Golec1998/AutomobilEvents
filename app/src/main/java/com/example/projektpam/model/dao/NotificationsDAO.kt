package com.example.projektpam.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projektpam.model.entities.NotificationsData

@Dao
interface NotificationsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification : NotificationsData)

    @Query("DELETE FROM notifications WHERE id LIKE :id")
    fun deleteNotification(id : Int)

    @Query("SELECT * FROM notifications ORDER BY date")
    fun readNotifications() : LiveData<List<NotificationsData>>

}