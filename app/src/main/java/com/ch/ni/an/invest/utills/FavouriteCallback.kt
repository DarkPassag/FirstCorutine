package com.ch.ni.an.invest.utills

import com.ch.ni.an.invest.model.AnimeChan

interface FavouriteCallback {
    fun checkInRoom(quote: AnimeChan): Boolean
}