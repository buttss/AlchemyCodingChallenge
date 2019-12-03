package com.butts.sean.alchemycodingchallenge.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Story::class), version = 1, exportSchema = false)
abstract class StoryRoomDatabase: RoomDatabase() {
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: StoryRoomDatabase? = null
        private const val DATABASE_NAME = "story_database"

        fun getDatabase(context: Context): StoryRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoryRoomDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}