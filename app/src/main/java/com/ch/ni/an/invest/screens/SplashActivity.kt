package com.ch.ni.an.invest.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.ch.ni.an.invest.BaseActivity
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.viewmodels.STATE
import com.ch.ni.an.invest.viewmodels.SplashViewModel

class SplashActivity: BaseActivity(R.layout.activity_splash) {

    private val myModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState :Bundle?) {
        super.onCreate(savedInstanceState)

        myModel.state.observe(this, {
            when (it) {
                STATE.SUCCESS -> newActivity()
                STATE.FAIL -> {
                    Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT)
                        .show()
                    newActivity()
                }
                else -> { }
            }
    })
}
    private fun newActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}