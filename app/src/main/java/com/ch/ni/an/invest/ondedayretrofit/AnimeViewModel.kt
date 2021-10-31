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


    private val _state = MutableLiveData<STATE>()
    val state: LiveData<STATE> = _state

    init {
        getRandomQuotes()
    }


    fun getRandomQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(STATE.PENDING)
            getRandomAnimeList()
        }
    }

    fun getQuotesByAnime(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(STATE.PENDING)
            getQuotesByAnimeTitle(url)
        }
    }

    fun getQuotesByCharacter(url: String){
        viewModelScope.launch(Dispatchers.IO){
            _state.postValue(STATE.PENDING)
            getQuotesByAnimeCharacter(url)
        }
    }

    fun setAnimeName(animeName: String) {
        _animeName.postValue(animeName)
    }


    private suspend fun getRandomAnimeList(){
        try {
            val response = Common.retrofit.getRandomQuotes()
            if(response.isSuccessful){
                _randomQuotes.postValue(response.body())
                _state.postValue(STATE.SUCCESS)
            } else {
                val error = response.errorBody()
                Log.e("ERROR", "$error")
            }
        } catch (e: Exception){
            _state.postValue(STATE.FAIL)
        }
    }


    private suspend fun getAvailableAnimeList() {
        try {
            val response = Common.retrofit.getAvailableAnime()
            if (response.isSuccessful) {
                val availableAnime = response.body()
                _listAvailableAnime.postValue(availableAnime)
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

    private suspend fun getQuotesByAnimeCharacter(url: String){
        try {
            val response = Common.retrofit.getQuotesByCharacter(url)
            if(response.isSuccessful){
                _quotesByCharacter.postValue(response.body())
                _state.postValue(STATE.SUCCESS)
            } else {
                val error = response.errorBody()
                Log.e("ERROR", "$error")
            }
        } catch (e:Exception){
            _state.postValue(STATE.FAIL)
        }
    }

    private suspend fun getQuotesByAnimeTitle(url: String) {
        try {
            val mService = Common.retrofit
            val quotesByAnime = mService.getQuotesByAnime(url)
            _animeQuotes.postValue(quotesByAnime)
            _state.postValue(STATE.SUCCESS)
        } catch (e: Exception) {
            _state.postValue(STATE.FAIL)
        }
    }
}

enum class STATE{
    PENDING, SUCCESS, FAIL
}

enum class QUOTES {
    RANDOM, TITLE, CHARACTER
}