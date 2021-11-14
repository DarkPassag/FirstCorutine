package com.ch.ni.an.invest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.NameItemRecyclerviewBinding
import com.ch.ni.an.invest.utills.RecyclerViewClickListener


class AnimeDiff(
    private val oldList :List<String>,
    private val newList :List<String>,
) : DiffUtil.Callback() {
    override fun getOldListSize() :Int = oldList.size

    override fun getNewListSize() :Int = newList.size

    override fun areItemsTheSame(oldItemPosition :Int, newItemPosition :Int) :Boolean {
        val item = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return item == newItem
    }

    override fun areContentsTheSame(oldItemPosition :Int, newItemPosition :Int) :Boolean {
        val item = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return item == newItem
    }

}

class ListAnimeTitleAdapter(
    private val onClick :RecyclerViewClickListener,
) : RecyclerView.Adapter<ListAnimeTitleAdapter.ListAnimeHolder>() {


    private var lastPosition :Int = -1

    var listAnime :List<String> = emptyList()
        set(value) {
            val callback = AnimeDiff(field, value)
            val result = DiffUtil.calculateDiff(callback)
            field = value
            result.dispatchUpdatesTo(this)
        }


    override fun onCreateViewHolder(parent :ViewGroup, viewType :Int) :ListAnimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = NameItemRecyclerviewBinding.inflate(inflater, parent, false)
        return ListAnimeHolder(bind)
    }

    override fun onBindViewHolder(holder :ListAnimeHolder, position :Int) {
        val anime = listAnime[position]
        holder.bind.nameTextView.text = anime
        holder.bind.nameTextView.setOnClickListener {
            onClick.clickListener(anime)
        }
        val animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation_fall_down)
        holder.itemView.startAnimation(animation)

    }

    override fun getItemCount() :Int = listAnime.size


    class ListAnimeHolder(val bind :NameItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(bind.root)
}


