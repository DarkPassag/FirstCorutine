package com.ch.ni.an.invest.viewmodels

import android.util.Log
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

    var listQuote: List<AnimeChan> = emptyList()

    init {
        loadListFavouriteQuote()
    }

    fun deleteQuote(quote: AnimeChan){
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteQuote(quote)
        }
    }


    fun loadListFavouriteQuote(){
        viewModelScope.launch(Dispatchers.IO) {
            listQuote = database.getAllForCheck()
        }
    }

    fun checkQuote(quote :AnimeChan): Boolean {
        Log.e("TAG", "${listQuote.contains(quote)}")
           return listQuote.contains(quote)
        }






}