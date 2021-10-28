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
    val listAvailableANime:LiveData<List<String>> = _listAvailableAnime

    private val _basicQuotes = MutableLiveData<List<AnimeChan>>()
    val basicQuotes: LiveData<List<AnimeChan>> = _basicQuotes

    private val _animeName = MutableLiveData<String>("naruto")
    val animeName: LiveData<String> = _animeName

    private val _animeQuotes = MutableLiveData<List<AnimeChan>>()
    val animeQuotes: LiveData<List<AnimeChan>> = _animeQuotes


    private val _state = MutableLiveData<STATE>()
    val state: LiveData<STATE> = _state

    init {
        getBasicQuotes()
    }



    fun getBasicQuotes(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(STATE.PENDING)
            getBasicQuotes1()
        }
    }

    fun getQuotesByAnime1(url:String){
        viewModelScope.launch(Dispatchers.IO) {
            getQuotesByAnime(url)
        }
    }

    fun setAnimeName(animeName: String){
        _animeName.postValue(animeName)
    }



   private suspend fun getBasicQuotes1(){
       Log.e("TAG", "suspend fun started")
        try {
            val mService = Common.retrofit
            val availableAnime = mService.getAvailableAnime()
            _listAvailableAnime.postValue(availableAnime)
            Log.e("TAG", availableAnime.size.toString())
            _state.postValue(STATE.SUCCESS)
        } catch (e:Exception){
            Log.e("TAG", "$e")
            _state.postValue(STATE.FAIL)
        }
    }

    private suspend fun getQuotesByAnime(url:String){
        Log.e("TAG", "quotes by anime")
        try {
            val mService = Common.retrofit
            val query = "anime?title=$url"
            Log.e("TAG", query)
            val quotesByAnime = mService.getQuotesByAnime(query)
            _animeQuotes.postValue(quotesByAnime)
        } catch (e:Exception){
            Log.e("TAG", e.toString())
        }
    }
}

enum class STATE{
    PENDING, SUCCESS, FAIL
}