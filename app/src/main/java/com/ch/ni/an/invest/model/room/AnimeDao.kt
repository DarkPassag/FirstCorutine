package com.ch.ni.an.invest.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ch.ni.an.invest.model.AnimeChan


@Dao
interface AnimeDao {

    @Query("SELECT * FROM quotes")
    fun getAll(): LiveData<List<AnimeChan>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuote(quote: AnimeChan)

}