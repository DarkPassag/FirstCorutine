package com.ch.ni.an.invest.temple

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes_table")
data class AnimeChan(
    val anime :String? = null,
    val character :String? = null,
    @PrimaryKey val quote :String
)


@Entity(tableName = "NameCharacter")
data class CharactersAnime(
    @PrimaryKey val name: String
)