package com.ch.ni.an.invest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.room.AnimeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyQuotesViewModel: ViewModel() {

    private val database = AnimeDatabase.get().animeDao()

    private val _myQuotes: LiveData<List<AnimeChan>> = database.getAll()
    val myQuotes: LiveData<List<AnimeChan>> = _myQuotes

    fun deleteQuote(quote: AnimeChan){
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteQuote(quote)
        }
    }


}