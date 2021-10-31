package com.ch.ni.an.invest


import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ch.ni.an.invest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState :Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        backGroundColor()
        setContentView(bind.root)
    }

    private fun backGroundColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawableResource(R.drawable.gradient_1)
    }

}