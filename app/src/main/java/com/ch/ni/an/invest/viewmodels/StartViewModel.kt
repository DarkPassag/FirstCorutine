package com.ch.ni.an.invest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.retrofit.Common
import com.ch.ni.an.invest.model.room.AnimeDatabase
import com.ch.ni.an.invest.roomAnimeChar.DatabaseCharacterAnime_Impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class StartViewModel: ViewModel() {

    private val database = AnimeDatabase.get().animeDao()

    private var listQuotes: List<AnimeChan> = emptyList()

    private val _favourite: MutableLiveData<Boolean> = MutableLiveData()
    val favourite: LiveData<Boolean> = _favourite


    fun favouriteButton(animeChan :AnimeChan) {
        when (checkInRoom(animeChan)) {
            true -> deleteQuoteFromDatabase(animeChan)
            false -> addFavouriteQuote(animeChan)
        }
    }

   fun checkInRoom(animeChan :AnimeChan) :Boolean {
       return listQuotes.contains(animeChan)
    }

    private fun addFavouriteQuote(animeChan :AnimeChan) {
        viewModelScope.launch(Dispatchers.IO) {
            database.insertQuote(animeChan)
            updateList()
            _favourite.postValue(true)
        }
    }


    private fun deleteQuoteFromDatabase(animeChan :AnimeChan) {
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteQuote(animeChan)
            updateList()
            _favourite.postValue(false)
        }
    }

    private suspend fun updateList() {
        listQuotes = database.getAllForCheck()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            updateList()
        }
    }


}