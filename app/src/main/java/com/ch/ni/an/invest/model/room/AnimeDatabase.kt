package com.ch.ni.an.invest.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ch.ni.an.invest.model.AnimeChan


@Database(entities = [AnimeChan::class], version = 1, exportSchema = false)
abstract class AnimeDatabase: RoomDatabase() {
    abstract fun animeDao(): AnimeDao

    companion object {

        @Volatile
        private var INSTANCE: AnimeDatabase? = null

        fun getDatabase(context :Context): AnimeDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnimeDatabase::class.java,
                    "quotes"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

        fun get(): AnimeDatabase {
            return INSTANCE ?:
            throw IllegalStateException("Database must be initialized")
        }

    }
}