package com.ch.ni.an.invest.model

data class AnimePerson(
    val name :NameCharacter,
    val image :PhotoCharacter,
)

data class NameCharacter(
    val fullName :String,
    val nativeName :String,
)


data class PhotoCharacter(
    val url :String,
)