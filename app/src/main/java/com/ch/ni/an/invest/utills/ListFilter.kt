package com.ch.ni.an.invest.utills

import android.util.Log
import com.ch.ni.an.invest.model.AnimeChan

class ListFilter {

    private val mainList: MutableList<AnimeChan> = mutableListOf()

    fun filter(quotes:List<AnimeChan>): List<AnimeChan>{

        return if(mainList.isEmpty()){
            addNewList(quotes)
            Log.e("List", "${mainList.size}")
            quotes
        } else {
            val tempList: MutableList<AnimeChan> = quotes.filter { !mainList.contains(it) }.toMutableList()
            addNewList(quotes)
            Log.e("List", "${mainList.size}")
            tempList
        }




    }


    private fun addNewList(quotes:List<AnimeChan>){
        mainList.addAll(quotes)
    }

    fun clear(){
        mainList.clear()
    }

}