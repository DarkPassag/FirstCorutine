package com.ch.ni.an.invest.utills

import com.ch.ni.an.invest.model.AnimeChan

interface RecyclerViewClickListener {
    fun clickListener(animeName: String)

    fun addQuote(animeChan: AnimeChan)

}