package com.ch.ni.an.invest.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.CharactersAnime


@Dao
interface AnimeDao {

    @Query("SELECT * FROM quotes")
    fun getAll(): LiveData<List<AnimeChan>>

    @Query("SELECT * FROM quotes")
    suspend fun getAllForCheck(): List<AnimeChan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: AnimeChan)

    @Delete
    suspend fun deleteQuote(quote :AnimeChan)

    @Query("SELECT * FROM characters_table")
    suspend fun getAllCharacter() : List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCharacter(animeCharacter :CharactersAnime)


}