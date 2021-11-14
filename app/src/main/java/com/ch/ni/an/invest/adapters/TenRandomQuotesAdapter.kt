package com.ch.ni.an.invest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.RandomItemRecyclerviewBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.FavouriteAnimeChan
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.RecyclerViewClickListener

class TenRandomQuotesAdapter(
    private val clickListener :RecyclerViewClickListener,
    private val favouriteCheck :FavouriteCallback,
) : RecyclerView.Adapter<TenRandomQuotesAdapter.AnimeStartViewHolder>() {

    var randomQuotes :List<AnimeChan> = emptyList()
        set(value) {
            val animeDiffCallback = RandomAnimeDiffUtil(field, value)
            val result = DiffUtil.calculateDiff(animeDiffCallback)
            field = value
            result.dispatchUpdatesTo(this)
        }

    class AnimeStartViewHolder(
        val bind :RandomItemRecyclerviewBinding,
    ) : RecyclerView.ViewHolder(bind.root)


    override fun onCreateViewHolder(parent :ViewGroup, viewType :Int) :AnimeStartViewHolder {
        val bind = RandomItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        return AnimeStartViewHolder(bind)
    }

    override fun onBindViewHolder(holder :AnimeStartViewHolder, position :Int) {
        val item = randomQuotes[position]
        holder.bind.animeNameTextView.text = item.anime
        holder.bind.animeCharacterTextView.text = item.character
        holder.bind.animeQuoteTextView.text = item.quote
        val favouriteButton = holder.bind.favouriteButton
        val newItem = FavouriteAnimeChan(anime = item.anime!!,
            character = item.character!!,
            quote = item.quote)
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

    override fun getItemCount() :Int = randomQuotes.size

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

class RandomAnimeDiffUtil(
    private val oldList :List<AnimeChan>,
    private val newList :List<AnimeChan>,
) : DiffUtil.Callback() {
    override fun getOldListSize() :Int = oldList.size

    override fun getNewListSize() :Int = newList.size

    override fun areItemsTheSame(oldItemPosition :Int, newItemPosition :Int) :Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.quote == newItem.quote
    }

    override fun areContentsTheSame(oldItemPosition :Int, newItemPosition :Int) :Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}