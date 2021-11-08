package com.ch.ni.an.invest

import android.app.Application
import android.content.Context
import com.ch.ni.an.invest.model.room.AnimeDatabase
import com.ch.ni.an.invest.roomAnimeChar.DatabaseCharacterAnime
import com.ch.ni.an.invest.roomAnimeChar.TempDatabase

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        AnimeDatabase.getDatabase(this)
        DatabaseCharacterAnime.getDatabase(this as Context)
    }
}