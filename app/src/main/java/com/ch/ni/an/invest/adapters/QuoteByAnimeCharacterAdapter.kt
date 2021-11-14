package com.ch.ni.an.invest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ch.ni.an.invest.databinding.RecyclerviewItemQuotesByCharacterBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.LoadImage
import com.ch.ni.an.invest.utills.RecyclerViewClickListener


class QuoteByAnimeCharacterAdapter(
    private val clickListener :RecyclerViewClickListener,
    private val favouriteCheck :FavouriteCallback,
    private val getImage :LoadImage,
) : PagingDataAdapter<AnimeChan, QuotesByAnimeCharacterHolder>(AnimeDiffUtil()) {


    override fun onBindViewHolder(holder :QuotesByAnimeCharacterHolder, position :Int) {
        val item = getItem(position)
        holder.bind(item!!)
    }

    override fun onCreateViewHolder(
        parent :ViewGroup,
        viewType :Int,
    ) :QuotesByAnimeCharacterHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerviewItemQuotesByCharacterBinding.inflate(inflater, parent, false)

        return QuotesByAnimeCharacterHolder(clickListener, favouriteCheck, getImage, binding)
    }

    class AnimeDiffUtil : DiffUtil.ItemCallback<AnimeChan>() {

        override fun areItemsTheSame(oldItem :AnimeChan, newItem :AnimeChan) :Boolean {
            return oldItem.quote == newItem.quote
        }

        override fun areContentsTheSame(oldItem :AnimeChan, newItem :AnimeChan) :Boolean {
            return oldItem.quote == newItem.quote
        }
    }


}
