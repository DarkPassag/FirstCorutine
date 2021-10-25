package com.ch.ni.an.invest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.model.AnimeChan

class AnimeAdapter: ListAdapter<AnimeChan ,AnimeAdapter.AnimeHolder>(DiffUtill()) {

    class AnimeHolder(view: View): RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return AnimeHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeHolder, position: Int) {
        TODO()
    }




    }
class DiffUtill: DiffUtil.ItemCallback<AnimeChan>(){
    override fun areItemsTheSame(oldItem: AnimeChan, newItem: AnimeChan): Boolean {
        return oldItem.quote == newItem.quote
    }

    override fun areContentsTheSame(oldItem: AnimeChan, newItem: AnimeChan): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
