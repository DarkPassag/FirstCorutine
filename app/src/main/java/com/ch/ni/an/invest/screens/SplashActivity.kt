package com.ch.ni.an.invest.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.ch.ni.an.invest.BaseActivity
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.utills.ANIME
import com.ch.ni.an.invest.utills.CHARACTER
import com.ch.ni.an.invest.utills.FIRST_LAUNCH
import com.ch.ni.an.invest.utills.QUOTE
import com.ch.ni.an.invest.viewmodels.STATE
import com.ch.ni.an.invest.viewmodels.SplashViewModel

class SplashActivity: BaseActivity(R.layout.activity_splash) {

    private val myModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState :Bundle?) {
        super.onCreate(savedInstanceState)

        myModel.randomQuote.observe(this, {
            it?.let {
                newActivitySuccess(it)
            }

        })

        myModel.state.observe(this, {
            when (it) {
                STATE.SUCCESS -> {}
                STATE.FAIL -> showToast()
                else -> { }
            }
    })
}
    private fun newActivitySuccess(quote:AnimeChan) {
        val intent = Intent(this, MainActivity::class.java)
        putQuote(quote)
        startActivity(intent)
        finish()
    }

    private fun putQuote(quote: AnimeChan){
        val sharedPreferences = getSharedPreferences(FIRST_LAUNCH, 0)
        sharedPreferences.edit().apply {
            putString(ANIME, quote.anime)
            putString(CHARACTER, quote.character)
            putString(QUOTE, quote.quote)
        }.apply()
    }



    private fun showToast(){
        Toast.makeText(
            this, R.string.no_internet, Toast.LENGTH_LONG
        ).show()
        newActivityFail()
    }
    private fun newActivityFail(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}