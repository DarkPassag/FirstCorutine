package com.ch.ni.an.invest.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.RandomItemRecyclerviewBinding
import com.ch.ni.an.invest.model.AnimeChan

class AnimeStartAdapter: RecyclerView.Adapter<AnimeStartAdapter.AnimeStartViewHolder>() {

    var randomQuotes: List<AnimeChan> = emptyList()
        set(value) {
            val animeDiffCallback = RandomAnimeDiffUtil(field, value)
            val result = DiffUtil.calculateDiff(animeDiffCallback)
            field = value
            result.dispatchUpdatesTo(this)
        }

    class AnimeStartViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeStartViewHolder {
        val bind = LayoutInflater.from(parent.context).inflate(R.layout.random_item_recyclerview, parent, false)
        return AnimeStartViewHolder(bind)
    }

    override fun onBindViewHolder(holder: AnimeStartViewHolder, position: Int) {
        val item = randomQuotes[position]

    }

    override fun getItemCount(): Int = randomQuotes.size
}

class RandomAnimeDiffUtil(
    private val oldList: List<AnimeChan>,
    private val newList: List<AnimeChan>
    ) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.quote == newItem.quote
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}