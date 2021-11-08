package com.ch.ni.an.invest.utills

interface LoadImage {
   suspend fun loadImage(characterName: String) : String
}