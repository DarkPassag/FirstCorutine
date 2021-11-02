package com.ch.ni.an.invest.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.room.AnimeDatabase

class MyQuotesViewModel: ViewModel() {

    private val database = AnimeDatabase.get().animeDao()

    private val _myQuotes: LiveData<List<AnimeChan>> = database.getAll()
    val myQuotes: LiveData<List<AnimeChan>> = _myQuotes


}