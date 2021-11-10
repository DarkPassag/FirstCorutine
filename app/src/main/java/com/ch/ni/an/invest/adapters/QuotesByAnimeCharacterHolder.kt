package com.ch.ni.an.invest.adapters

import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.RecyclerviewItemQuotesByCharacterBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.LoadImage
import com.ch.ni.an.invest.utills.RecyclerViewClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuotesByAnimeCharacterHolder(
    private val clickListener :RecyclerViewClickListener,
    private val favouriteCheck :FavouriteCallback,
    private val getImage :LoadImage,
    private val binding :RecyclerviewItemQuotesByCharacterBinding,
) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item :AnimeChan){
            binding.quoteByCharacterTextView.text = item.quote
            val favouriteButton = binding.favouriteButton
            var flag = chekFlag(item)
            checkFavourite(favouriteButton, item)
            favouriteButton.setOnClickListener {
                flag = if (flag == 0) {
                    clickListener.deleteQuote(item)
                    favouriteButton.setImageResource(R.drawable.ic_no_favourite)
                    1
                } else {
                    clickListener.addQuote(item)
                    favouriteButton.setImageResource(R.drawable.ic_favourite)
                    0
                }
            }
            CoroutineScope(Dispatchers.IO).launch {
                val url = getImage.loadImage(item.character!!)
                CoroutineScope(Dispatchers.Main).launch {
                    binding.characterPhotoImageView.load(url){
                        transformations(RoundedCornersTransformation(50f))
                        crossfade(true)
                        crossfade(300)
                        error(R.drawable.ic_image)
                    }
                }
            }
        }

    private fun checkFavourite(imageButton :ImageButton, item: AnimeChan){
        if(favouriteCheck.checkInRoom(item)){
            imageButton.setImageResource(R.drawable.ic_favourite)
        } else {
            imageButton.setImageResource(R.drawable.ic_no_favourite)
        }
    }
    private fun chekFlag(item :AnimeChan): Int{
        return if(favouriteCheck.checkInRoom(item)) 0 else 1
    }
    }