package com.ch.ni.an.invest

import android.app.Application
import android.content.Context
import com.ch.ni.an.invest.model.room.AnimeDatabase

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        AnimeDatabase.getDatabase(this)
    }
}