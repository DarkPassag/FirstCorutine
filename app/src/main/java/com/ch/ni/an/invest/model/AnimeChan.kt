package com.ch.ni.an.invest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "quotes")
data class AnimeChan(
    val anime: String,
    val character: String,
  @PrimaryKey val quote: String)


@Entity(tableName = "characters_with_photo")
data class CharacterWithPhoto(
    @PrimaryKey @ColumnInfo(name = "character") val name :String,
    @ColumnInfo(name = "url") val url: String
)

@Entity(tableName = "favourite_quote")
data class FavouriteAnimeChan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "anime") val anime: String,
    @ColumnInfo(name = "character") val character: String,
    @ColumnInfo(name = "quote") val quote :String
)

