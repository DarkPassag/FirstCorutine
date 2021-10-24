package com.ch.ni.an.invest.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class InvestViewModel: ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private val _color = MutableLiveData<Int>()
    val color: LiveData<Int> = _color

    private val _status = MutableLiveData<STATUS>(STATUS.PENDING)
    val status:LiveData<STATUS> = _status


    fun changeText(){
        viewModelScope.launch {
            delay(5000)
            val newText = "paramparam"
            _text.postValue(newText)
            delay(10000)
            val thisISText = "I go from future"
            _text.postValue(thisISText)
        }
    }

    fun asyncChangeTextAndColor(){
        _status.value = STATUS.PENDING
//        viewModelScope.launch {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val int = Random.nextInt(1,10)
                delay(5000)
                val text = "This is sync text"
//                _color.postValue(int)
//                _text.postValue(text)
                _color.value = int
                _text.value = text
                _status.postValue(STATUS.OK)
            } catch (e:Exception){
                _text.postValue(e.toString())
                _status.postValue(STATUS.ERROR)
            }


        }
    }

    fun changeStatus(){
        viewModelScope.launch {
            delay(2000)
            _status.value = STATUS.OK
        }

    }
}

enum class STATUS {
    PENDING, OK, ERROR
}