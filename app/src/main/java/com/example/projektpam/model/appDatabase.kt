package com.example.projektpam.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projektpam.model.dao.EventsDAO
import model.EventsJSON

@Database(entities = [EventsJSON::class], version = 1, exportSchema = false)
abstract class appDatabase : RoomDatabase() {

    abstract fun eventsDAO() : EventsDAO

    companion object {
        @Volatile
        private var INSTANCE : appDatabase? = null

        fun getDatabase(context : Context) : appDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                    "appDatabase"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}