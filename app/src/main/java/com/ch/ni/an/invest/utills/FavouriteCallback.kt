package com.ch.ni.an.invest.utills


import com.ch.ni.an.invest.model.FavouriteAnimeChan

interface FavouriteCallback {
    fun checkInRoom(quote:FavouriteAnimeChan): Boolean

}