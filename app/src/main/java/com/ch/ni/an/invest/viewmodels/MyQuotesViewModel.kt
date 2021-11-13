package com.ch.ni.an.invest.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch.ni.an.invest.model.FavouriteAnimeChan
import com.ch.ni.an.invest.model.room.AnimeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyQuotesViewModel: ViewModel() {

    private val database = AnimeDatabase.getDatabase().animeDao()


    val myQuotes: LiveData<List<FavouriteAnimeChan>> = database.getFavouriteQuote()

    private var listQuote: List<FavouriteAnimeChan> = emptyList()




    fun deleteQuote(quote: FavouriteAnimeChan){
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteQuote(quote)
            loadFavouriteQuotes()
        }
    }



    fun checkQuote(quote :FavouriteAnimeChan): Boolean {
            loadFavouriteQuotes()
           return listQuote.contains(quote)
        }

    private fun loadFavouriteQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val tempList :List<FavouriteAnimeChan> = database.loadFavouriteQuotes()
            listQuote = tempList
        }
    }








}