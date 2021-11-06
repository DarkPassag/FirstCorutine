package com.ch.ni.an

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.model.retrofit.AnimeViewModel

class TestActivity : AppCompatActivity() {
    private val myModel: AnimeViewModel by viewModels()

    override fun onCreate(savedInstanceState :Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        myModel.urlImage.observe(this, {
             val image = findViewById<ImageView>(R.id.characterPhotoImageView)
            image.load(it){
                crossfade(true)
                crossfade(1000)
                transformations(RoundedCornersTransformation(50f))
            }
        })
    }
}