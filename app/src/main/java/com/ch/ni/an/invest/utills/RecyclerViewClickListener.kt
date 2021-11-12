package com.ch.ni.an.invest.utills

import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.FavouriteAnimeChan

interface RecyclerViewClickListener {
    fun clickListener(anime :String)

    fun addQuote(animeChan:FavouriteAnimeChan)

    fun deleteQuote(animeChan :FavouriteAnimeChan)

}