package com.ch.ni.an.invest.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.CharacterWithPhoto
import com.ch.ni.an.invest.model.FavouriteAnimeChan


@Dao
interface AnimeDao {

    @Query("SELECT * FROM quotes WHERE anime=:title")
    suspend fun getQuotesFromDB(title: String): List<AnimeChan>

    @Query("SELECT DISTINCT character FROM quotes")
    suspend fun getNameCharacters(): List<String>

    @Query("SELECT * FROM favourite_quote")
    fun getFavouriteQuote(): LiveData<List<FavouriteAnimeChan>>

    @Query("SELECT * FROM characters_with_photo")
    suspend fun getCharacterWithPhoto(): List<CharacterWithPhoto>

    @Query("SELECT url FROM characters_with_photo WHERE character=:character ")
    suspend fun getURL(character:String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: FavouriteAnimeChan)

    @Delete
    suspend fun deleteQuote(quote :FavouriteAnimeChan)

    @Query("SELECT * FROM favourite_quote")
    suspend fun loadFavouriteQuotes(): List<FavouriteAnimeChan>








}