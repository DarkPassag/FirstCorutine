package com.ch.ni.an.invest.screens

import com.ch.ni.an.invest.model.AnimeChan

interface RecyclerViewClickListener {
    fun clickListener(animeName: String)

    fun addQuote(animeChan: AnimeChan)

}