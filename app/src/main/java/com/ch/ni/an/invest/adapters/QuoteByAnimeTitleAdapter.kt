package com.ch.ni.an.invest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.RecyclerviewItemBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.LoadImage
import com.ch.ni.an.invest.utills.RecyclerViewClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteByAnimeTitleAdapter(
    private val clickListener :RecyclerViewClickListener,
    private val favouriteCheck :FavouriteCallback,
    private val getImage :LoadImage,
) : PagingDataAdapter<AnimeChan, QuotesByAnimeTitleHolder>(AnimeDiffUtil()) {


    override fun onCreateViewHolder(parent :ViewGroup, viewType :Int) :QuotesByAnimeTitleHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = RecyclerviewItemBinding.inflate(inflater, parent, false)
        return QuotesByAnimeTitleHolder(clickListener, favouriteCheck, getImage, bind)
    }

    override fun onBindViewHolder(holder :QuotesByAnimeTitleHolder, position :Int) {
        val item = getItem(position)
        holder.bind(item!!)

    }
    class AnimeDiffUtil : DiffUtil.ItemCallback<AnimeChan>() {

        override fun areItemsTheSame(oldItem :AnimeChan, newItem :AnimeChan) :Boolean {
            return oldItem.quote == newItem.quote
        }

        override fun areContentsTheSame(oldItem :AnimeChan, newItem :AnimeChan) :Boolean {
            return oldItem == newItem
        }
    }
}
