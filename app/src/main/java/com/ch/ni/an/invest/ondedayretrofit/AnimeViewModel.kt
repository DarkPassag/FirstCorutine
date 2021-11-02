package com.ch.ni.an.invest.ondedayretrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch.ni.an.invest.model.AnimeChan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class AnimeViewModel: ViewModel() {


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
            _state.postValue(STATE.PENDING)

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
            _state.postValue(STATE.PENDING)
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
                    _state.postValue(STATE.SUCCESS)
                } else {
                    val error = response.errorBody()
                    Log.e("ERROR", "$error")
                }

            } catch (e: Exception) {
                Log.e("TAG", "$e")
                _state.postValue(STATE.FAIL)
            }
        }

    }


    private suspend fun getQuotesByAnimeTitle(url: String) {
        try {
            val mService = Common.retrofit
//            _animeQuotes.postValue()
            _state.postValue(STATE.SUCCESS)
        } catch (e: Exception) {
            _state.postValue(STATE.FAIL)
        }
    }
}

enum class STATE{
    PENDING, SUCCESS, FAIL
}
