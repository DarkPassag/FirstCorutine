package com.ch.ni.an.invest.model.room

import androidx.lifecycle.LiveData
import com.ch.ni.an.invest.model.AnimeChan

class AnimeRepository(private val animeDao :AnimeDao) {

    val readAllQuotes: LiveData<List<AnimeChan>> = animeDao.getAll()

   suspend fun addQuote(quote: AnimeChan){
        animeDao.insertQuote(quote)
    }
}