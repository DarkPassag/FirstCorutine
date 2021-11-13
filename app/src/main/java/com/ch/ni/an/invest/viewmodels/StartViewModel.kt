package com.ch.ni.an.invest.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.FavouriteAnimeChan
import com.ch.ni.an.invest.model.room.AnimeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StartViewModel: ViewModel() {

    private val database = AnimeDatabase.getDatabase().animeDao()

    private var listQuotes: List<FavouriteAnimeChan> = emptyList()

    private val _favourite: MutableLiveData<Boolean> = MutableLiveData()
    val favourite: LiveData<Boolean> = _favourite


    fun favouriteButton(animeChan :FavouriteAnimeChan) {
        when (checkInRoom(animeChan)) {
            true -> deleteQuoteFromDatabase(animeChan)
            false -> addFavouriteQuote(animeChan)
        }
    }

   fun checkInRoom(animeChan :FavouriteAnimeChan) :Boolean {
       return listQuotes.contains(animeChan)
    }

    init {
        loadFavouriteQuotes()
    }

    private fun addFavouriteQuote(animeChan :FavouriteAnimeChan) {
        viewModelScope.launch(Dispatchers.IO) {
            database.insertQuote(animeChan)
            _favourite.postValue(true)
            loadFavouriteQuotes()
        }
    }


    private fun deleteQuoteFromDatabase(animeChan :FavouriteAnimeChan) {
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteQuote(animeChan)
            _favourite.postValue(false)
            loadFavouriteQuotes()
        }
    }

    private fun loadFavouriteQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val tempList :List<FavouriteAnimeChan> = database.loadFavouriteQuotes()
            listQuotes = tempList
        }
    }






}