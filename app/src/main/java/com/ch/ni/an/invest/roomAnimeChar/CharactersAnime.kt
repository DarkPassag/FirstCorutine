package com.ch.ni.an.invest.roomAnimeChar

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "NameCharacter")
data class CharactersAnime(
    @PrimaryKey() val name: String
    )
