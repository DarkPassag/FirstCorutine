package com.ch.ni.an.invest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.RecyclerviewItemQuotesByCharacterBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.LoadImage
import com.ch.ni.an.invest.utills.RecyclerViewClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteByAnimeCharacterAdapter(
    private val loadImage :LoadImage,
    private val favouriteCheck :FavouriteCallback,
    private val clickListener:RecyclerViewClickListener
): RecyclerView.Adapter<QuoteByAnimeCharacterAdapter.QuoteByAnimeCharacterHolder>() {

    class QuoteByAnimeCharacterHolder(
        val bind: RecyclerviewItemQuotesByCharacterBinding
        ): RecyclerView.ViewHolder(bind.root){

        }

    var setList: List<AnimeChan> = emptyList()
    set(value) {
        val diffCallback = AnimeQuoteDiffUtil(field, value)
        val result = DiffUtil.calculateDiff(diffCallback)
        field = value
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent :ViewGroup, viewType :Int) :QuoteByAnimeCharacterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = RecyclerviewItemQuotesByCharacterBinding.inflate(inflater, parent, false)
        return QuoteByAnimeCharacterHolder(bind)
    }

    override fun onBindViewHolder(holder :QuoteByAnimeCharacterHolder, position :Int) {
        val item = setList[position]
        holder.bind.quoteByCharacterTextView.text = item.quote
        val favouriteButton = holder.bind.favouriteButton
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
            val url = loadImage.loadImage(item.character!!)
            CoroutineScope(Dispatchers.Main).launch {
                holder.bind.characterPhotoImageView.load(url)
            }
        }

    }

    override fun getItemCount() :Int = setList.size


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
