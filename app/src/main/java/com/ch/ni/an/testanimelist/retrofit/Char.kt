package com.ch.ni.an.testanimelist.retrofit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CharAnime(
    @Json(name = "data") val char: Character
    )

@JsonClass(generateAdapter = true)
data class Character(
    @Json(name = "name") val name :Name,
    @Json(name = "image") val image: Image)


@JsonClass(generateAdapter = true)
data class Name(
    @Json(name = "full") val full: String,
    @Json(name= "native") val native: String
)

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "medium") val medium: String
)