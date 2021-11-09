package com.ch.ni.an.invest.temple

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [AnimeChan::class, CharactersAnime::class], version = 1, exportSchema = true)
abstract class AnimeDatabase: RoomDatabase() {
    abstract fun animeDao() :AnimeDao

    companion object {

        @Volatile
        private var INSTANCE :AnimeDatabase? = null

        fun getDatabase(context :Context) :AnimeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context,
                AnimeDatabase::class.java,
                "AnimeDatabase")
                    .createFromAsset("NameCharacter")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}