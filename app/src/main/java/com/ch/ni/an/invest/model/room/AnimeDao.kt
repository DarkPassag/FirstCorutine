package com.ch.ni.an.invest.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ch.ni.an.invest.model.AnimeChan


@Dao
interface AnimeDao {

    @Query("SELECT * FROM quotes")
    fun getAll(): LiveData<List<AnimeChan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: AnimeChan)

    @Delete
    suspend fun deleteQuote(quote :AnimeChan)

}