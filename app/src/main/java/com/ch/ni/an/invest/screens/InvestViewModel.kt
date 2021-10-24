package com.ch.ni.an.invest.screens

import android.graphics.Color
import android.util.Log
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

    private val _color = MutableLiveData<COLOR>()
    val color: LiveData<COLOR> = _color

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
                delay(5000)
                val text = "This is sync text"
                val color = COLOR(randomColor(),randomColor(),randomColor())
                _color.postValue(color)
                _text.postValue(text)
//                _color.value = color
//                _text.value = text
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
    fun changeColor(){
        val color = COLOR(randomColor(),randomColor(),randomColor())
        _color.value = color
    }

    fun randomColor(): Int{
        return Random.nextInt(0,255)
    }
}

enum class STATUS {
    PENDING, OK, ERROR
}

data class COLOR(val red: Int, val green: Int, val black: Int)