package com.ch.ni.an.invest.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "quotes")
data class AnimeChan(
    val anime: String? = null,
    val character: String? = null,
  @PrimaryKey val quote: String){
}

