package com.ch.ni.an.testanimelist.retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

class testViewModel: ViewModel() {

    val testObserve: MutableLiveData<String> = MutableLiveData()



    suspend fun post(characterName :String) {
        val paramObject = JSONObject()
        paramObject.put( "query","query { Character (search: \"naruto\") { name { full native } image { medium } } }"
        )
        Log.e("POST", paramObject.toString())
        val retrofit1 = Common.retrofitHelper.getCharByName(paramObject.toString())
        if(retrofit1.isSuccessful){
            Log.e("Responce", "${retrofit1.body()}")
        } else {
            Log.e("erorrrr", "${retrofit1.errorBody()}")
        }
    }

    fun getData(){
        viewModelScope.launch(Dispatchers.IO){
            post("naruto")
            delay(1000)
            Log.e("TestResponse", "ww")
        }
    }

    init {
        getData()
    }




}