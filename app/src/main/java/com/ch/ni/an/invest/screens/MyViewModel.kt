package com.ch.ni.an.invest.screens

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch.ni.an.invest.Retrofit.ApiInterface
import com.ch.ni.an.invest.model.AnimeChan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.random.Random

class TestViewModel: ViewModel() {

    private val _tenQuotes = MutableLiveData<List<AnimeChan>>()
    val tenQuotes = _tenQuotes


    fun get10Quotes(){
        viewModelScope.launch {
            try {
                val tenQuotes = ApiInterface.create().getTenQuotes()
                _tenQuotes.postValue(tenQuotes)
            } catch (e:Exception){
                Log.d("TAG", e.toString())
            }

        }
    }


}