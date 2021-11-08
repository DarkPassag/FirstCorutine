package com.ch.ni.an.invest.roomAnimeChar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CharactersDao {

    @Query("SELECT * FROM NameCharacter")
    suspend fun getAllCharacter() : List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacter(animeCharacter :CharactersAnime)

}