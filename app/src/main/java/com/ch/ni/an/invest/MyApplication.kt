package com.ch.ni.an.invest

import android.app.Application
import com.ch.ni.an.invest.model.room.AnimeDatabase


class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        AnimeDatabase.initDatabase(this)
    }
}