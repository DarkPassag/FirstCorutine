package com.ch.ni.an.invest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch.ni.an.invest.model.AnimeChan
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
        }
    }



    fun checkQuote(quote :FavouriteAnimeChan): Boolean {
        Log.e("TAG", "${listQuote.contains(quote)}")
           return listQuote.contains(quote)
        }








}