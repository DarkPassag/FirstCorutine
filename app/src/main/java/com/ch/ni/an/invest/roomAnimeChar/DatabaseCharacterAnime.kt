package com.ch.ni.an.invest.roomAnimeChar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.lang.IllegalStateException


@Database(entities = [CharactersAnime::class], version = 1, exportSchema = false)
abstract class DatabaseCharacterAnime(): RoomDatabase(){
    abstract fun CharactersDao(): CharactersDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseCharacterAnime? = null

        fun getDatabase(context :Context): DatabaseCharacterAnime{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    DatabaseCharacterAnime::class.java,
                    "NameCharacter"
                ).build()
                INSTANCE = instance
                return instance
            }
    }

        fun get(): DatabaseCharacterAnime{
            return INSTANCE ?:
            throw IllegalStateException("no database")
        }
}









}