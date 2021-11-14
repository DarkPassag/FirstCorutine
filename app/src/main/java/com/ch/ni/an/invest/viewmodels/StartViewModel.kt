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


class StartViewModel : ViewModel() {

    private val database = AnimeDatabase.getDatabase().animeDao()

    private var listQuotes :List<FavouriteAnimeChan> = emptyList()

    private val _favourite :MutableLiveData<Boolean> = MutableLiveData()
    val favourite :LiveData<Boolean> = _favourite


    fun favouriteButton(animeChan :FavouriteAnimeChan) {
        addFavouriteQuote(animeChan)
    }

    fun checkInRoom(animeChan :FavouriteAnimeChan) :Boolean {
        loadFavouriteQuotes()
        val index = listQuotes.indexOfFirst { it.quote == animeChan.quote }
        _favourite.postValue(index != -1)
        Log.e("Statement update", "${index != -1}")
        return index != -1
    }

    init {
        loadFavouriteQuotes()
    }

    private fun addFavouriteQuote(favouriteAnimeChan :FavouriteAnimeChan) {
        viewModelScope.launch() {
            val index = listQuotes.indexOfFirst { it.quote == favouriteAnimeChan.quote }
            if (index == -1) {
                database.insertQuote(favouriteAnimeChan)
                _favourite.postValue(true)
                loadFavouriteQuotes()
            } else {
                database.deleteQuoteByQuote(favouriteAnimeChan.quote)
                _favourite.postValue(false)
                loadFavouriteQuotes()
            }
        }
    }

    fun loadFavouriteQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val tempList :List<FavouriteAnimeChan> = database.loadFavouriteQuotes()
            listQuotes = tempList
        }
    }


}