package com.ch.ni.an.invest.model.retrofit


import android.util.Log
import androidx.lifecycle.*
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.retrofit.STATE.*
import com.ch.ni.an.invest.model.room.AnimeDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class AnimeViewModel(): ViewModel() {


    private val database = AnimeDatabase.get()

    private val _listAvailableAnime = MutableLiveData<List<String>>()
    val listAvailableAnime: LiveData<List<String>> = _listAvailableAnime

    private val _randomQuotes = MutableLiveData<List<AnimeChan>>()
    val randomQuotes: LiveData<List<AnimeChan>> = _randomQuotes

    private val _animeName = MutableLiveData<String>()
    val animeName: LiveData<String> = _animeName

    private val _animeQuotes = MutableLiveData<List<AnimeChan>>()
    val animeQuotes: LiveData<List<AnimeChan>> = _animeQuotes

    private val _quotesByCharacter = MutableLiveData<List<AnimeChan>>()
    val quotesByAnimaCharacter: LiveData<List<AnimeChan>> = _quotesByCharacter

    private var _listAnime = emptyList<String>()
    val listAnime: List<String>
        get() = _listAnime

    var tempList: MutableList<String> = mutableListOf()


    private val _state = MutableLiveData<STATE>()
    val state: LiveData<STATE> = _state

    init {
        getAvailableAnimeList()
    }


    fun getRandomQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(PENDING)

        }
    }

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            listAnime.forEach {
                if (it.contains(query, ignoreCase = true)) {
                    tempList.add(it)
                    _listAvailableAnime.postValue(tempList)
                }
            }
        }
    }

    fun getQuotesByAnime(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(PENDING)
            getQuotesByAnimeTitle(url)
        }
    }


    fun getAvailableAnimeList() {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = Common.retrofit.getAvailableAnime()
                if (response.isSuccessful) {
                    val availableAnime = response.body()
                    _listAvailableAnime.postValue(availableAnime)
                    _listAnime = response.body()!!
                    _state.postValue(SUCCESS)
                } else {
                    val error = response.errorBody()
                    Log.e("ERROR", "$error")
                }

            } catch (e: Exception) {
                Log.e("TAG", "$e")
                _state.postValue(FAIL)
            }
        }

    }

    fun addQuote(quote :AnimeChan){
        viewModelScope.launch(Dispatchers.IO){
            database.animeDao().insertQuote(quote)
        }
    }


    private suspend fun getQuotesByAnimeTitle(url: String) {
        try {
            val response = Common.retrofit.getQuotesByAnimeName(url)
            if(response.isSuccessful){
                val quotes = response.body()
                _quotesByCharacter.postValue(quotes)
                _state.postValue(SUCCESS)
            } else {
                val error = response.errorBody()
                Log.e("ERROR", "$error")
            }
        } catch (e: Exception) {
            _state.postValue(FAIL)
        }
    }


}

enum class STATE{
    PENDING, SUCCESS, FAIL
}
