package com.ch.ni.an.invest.screens



import android.os.Bundle
import com.ch.ni.an.invest.BaseActivity
import com.ch.ni.an.invest.R

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState :Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolBar))
    }


}