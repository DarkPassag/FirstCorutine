package com.ch.ni.an.invest.viewmodels

import  android.util.Log
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

    init { }

    fun deleteQuote(quote: AnimeChan){
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteQuote(quote)
        }
    }

    fun checkQuote(quote :AnimeChan): Boolean{
        var contains: Boolean? = null
        contains = _myQuotes.value?.contains(quote)
        Log.e("Trouble", contains.toString())
        return contains ?: false
    }

}