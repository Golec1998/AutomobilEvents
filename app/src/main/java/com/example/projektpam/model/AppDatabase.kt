package com.example.projektpam.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projektpam.model.dao.EventsDAO
import com.example.projektpam.model.dao.FavEventsDAO
import com.example.projektpam.model.dao.NotificationsDAO
import com.example.projektpam.model.entities.FavEventsData
import com.example.projektpam.model.entities.NotificationsData
import model.EventsJSON

@Database(entities = [EventsJSON::class, FavEventsData::class, NotificationsData::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventsDAO() : EventsDAO
    abstract fun favEventsDAO() : FavEventsDAO
    abstract fun notificationsDAO() : NotificationsDAO

    companion object {
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context : Context) : AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appDatabase"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}