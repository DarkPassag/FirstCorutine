package com.ch.ni.an.invest.temple

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.roomAnimeChar.CharactersAnime

interface AnimeDao {

    @androidx.room.Query("SELECT * FROM NameCharacter")
    suspend fun getAllCharacter() : List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacter(animeCharacter :CharactersAnime)

    @androidx.room.Query("SELECT * FROM quotes_table")
    fun getAll():LiveData<List<AnimeChan>>

    @androidx.room.Query("SELECT * FROM quotes_table")
    suspend fun getAllForCheck(): List<AnimeChan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote:AnimeChan)

    @Delete
    suspend fun deleteQuote(quote :AnimeChan)



}