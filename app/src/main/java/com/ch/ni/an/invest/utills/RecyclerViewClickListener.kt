package com.ch.ni.an.invest.utills

import com.ch.ni.an.invest.model.AnimeChan

interface RecyclerViewClickListener {
    fun clickListener(anime :String)

    fun addQuote(animeChan: AnimeChan)

    fun deleteQuote(animeChan :AnimeChan)

}