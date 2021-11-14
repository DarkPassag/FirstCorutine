package com.ch.ni.an.invest.adapters

import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.RecyclerviewItemBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.FavouriteAnimeChan
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.LoadImage
import com.ch.ni.an.invest.utills.RecyclerViewClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuotesByAnimeTitleHolder(
    private val clickListener :RecyclerViewClickListener,
    private val favouriteCheck :FavouriteCallback,
    private val getImage :LoadImage,
    private val binding :RecyclerviewItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item :AnimeChan) {
        binding.characterNameTextView.setOnClickListener {
            clickListener.clickListener(item.character)
        }
        binding.characterNameTextView.text = item.character
        binding.quoteByCharacterTextView.text = item.quote
        CoroutineScope(Dispatchers.IO).launch {
            val urlForImage = getImage.loadImage(item.character)
            CoroutineScope(Dispatchers.Main).launch {
                binding.characterPhotoImageView.load(urlForImage) {
                    crossfade(true)
                    crossfade(1000)
                    transformations(RoundedCornersTransformation(36F))
                    error(R.drawable.ic_image)
                }

            }
        }


        val favouriteButton = binding.favouriteButton
        val newItem =
            FavouriteAnimeChan(anime = item.anime, character = item.character, quote = item.quote)
        var flag = chekFlag(newItem)
        checkFavourite(favouriteButton, newItem)
        favouriteButton.setOnClickListener {
            flag = if (flag == 0) {
                clickListener.deleteQuote(newItem)
                favouriteButton.setImageResource(R.drawable.ic_no_favourite)
                1
            } else {
                clickListener.addQuote(newItem)
                favouriteButton.setImageResource(R.drawable.ic_favourite)
                0
            }
        }
    }


    private fun checkFavourite(imageButton :ImageButton, item :FavouriteAnimeChan) {
        if (favouriteCheck.checkInRoom(item)) {
            imageButton.setImageResource(R.drawable.ic_favourite)
        } else {
            imageButton.setImageResource(R.drawable.ic_no_favourite)
        }
    }

    private fun chekFlag(item :FavouriteAnimeChan) :Int {
        return if (favouriteCheck.checkInRoom(item)) 0 else 1
    }
}
