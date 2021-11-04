package com.ch.ni.an.invest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.databinding.NameItemRecyclerviewBinding
import com.ch.ni.an.invest.utills.RecyclerViewClickListener


class AnimeDiff(
    private val oldList: List<String>,
    private val newList: List<String>): DiffUtil.Callback()
{
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val item = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return item == newItem
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val item = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return item == newItem
    }

}
class ListAnimeTitleAdapter(
    val onClick:RecyclerViewClickListener
    ): RecyclerView.Adapter<ListAnimeTitleAdapter.ListAnimeHolder>() {

    private lateinit var animeName :String

    var listAnime :List<String> = emptyList()
        set(value) {
            val DiffCallback = AnimeDiff(field, value)
            val result = DiffUtil.calculateDiff(DiffCallback)
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
        holder.bind.nametextView.text = anime
        holder.bind.nametextView.setOnClickListener {
            onClick.clickListener(anime)
        }


    }

    override fun getItemCount() :Int = listAnime.size


    class ListAnimeHolder(val bind :NameItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(bind.root)
}


