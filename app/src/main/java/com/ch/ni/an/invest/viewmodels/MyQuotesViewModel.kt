package com.ch.ni.an.invest.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch.ni.an.invest.model.FavouriteAnimeChan
import com.ch.ni.an.invest.model.room.AnimeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyQuotesViewModel : ViewModel() {

    private val database = AnimeDatabase.getDatabase().animeDao()


    val myQuotes :LiveData<List<FavouriteAnimeChan>> = database.getFavouriteQuote()

    private var listQuote :List<FavouriteAnimeChan> = emptyList()


    fun deleteQuote(quote :FavouriteAnimeChan) {
        viewModelScope.launch(Dispatchers.IO) {
            val index = database.loadFavouriteQuotes().indexOfFirst { it.quote == quote.quote }
            if (index != -1) database.deleteQuoteByQuote(quote.quote)
            loadFavouriteQuotes()
        }
    }


    fun checkQuote(quote :FavouriteAnimeChan) :Boolean {
        loadFavouriteQuotes()
        val index = listQuote.indexOfFirst { it.quote == quote.quote }
        return index != -1
    }

    fun loadFavouriteQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            listQuote = database.loadFavouriteQuotes()
        }
    }

    init {
        loadFavouriteQuotes()
    }


}