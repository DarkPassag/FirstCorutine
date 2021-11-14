package com.ch.ni.an.invest.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.CharacterWithPhoto
import com.ch.ni.an.invest.model.FavouriteAnimeChan


@Database(entities = [AnimeChan::class, CharacterWithPhoto::class, FavouriteAnimeChan::class],
    version = 1,
    exportSchema = false)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao() :AnimeDao

    companion object {

        @Volatile
        private var INSTANCE :AnimeDatabase? = null

        fun initDatabase(context :Context) :AnimeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    AnimeDatabase::class.java,
                    "AnimeDatabase").createFromAsset("AnimeDatabase.db").build()
                INSTANCE = instance
                return instance
            }

        }

        fun getDatabase() :AnimeDatabase {
            return INSTANCE ?: throw IllegalStateException("Database must be initialized")
        }

    }
}