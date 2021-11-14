package com.ch.ni.an.invest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.retrofit.Common
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class SplashViewModel : ViewModel() {

    private val _state :MutableLiveData<STATE> = MutableLiveData()
    val state :LiveData<STATE> = _state

    private val _randomQuote = MutableLiveData<AnimeChan>()
    val randomQuote :LiveData<AnimeChan> = _randomQuote

    private fun getQuote() {
        _state.postValue(STATE.PENDING)
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            try {
                val response = Common.retrofit.getRandomQuote()
                if (response.isSuccessful) {
                    val quote = response.body()
                    _randomQuote.postValue(quote)
                    _state.postValue(STATE.SUCCESS)
                } else {
                    val errorResponse = response.errorBody().toString()
                    Log.e("ErrorResponse", errorResponse)
                    _state.postValue(STATE.FAIL)
                }
            } catch (e :Exception) {
                _state.postValue(STATE.FAIL)
                Log.e("ExceptionException", e.toString())
            }
        }

    }

    init {
        getQuote()
    }

}