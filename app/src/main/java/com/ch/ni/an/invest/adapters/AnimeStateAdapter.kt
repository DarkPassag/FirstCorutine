package com.ch.ni.an.invest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.databinding.ItemProgressBinding

class AnimeStateAdapter: LoadStateAdapter<AnimeStateAdapter.ItemViewHolder>() {


    override fun getStateViewType(loadState :LoadState) = when(loadState){
        is LoadState.NotLoading -> error("Not supported")
        LoadState.Loading -> LOADING
        is LoadState.Error -> ERROR
    }


    private companion object{
        const val LOADING = 0
        const val  ERROR = 1
    }


  abstract  class ItemViewHolder(view:View): RecyclerView.ViewHolder(view) {
      abstract fun bind(state:LoadState)
    }



    override fun onBindViewHolder(holder :ItemViewHolder, loadState :LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent :ViewGroup, loadState :LoadState) :ItemViewHolder {
        return when(loadState){
            LoadState.Loading -> ProgressViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.Error -> ProgressViewHolder(LayoutInflater.from(parent.context), parent)
            is LoadState.NotLoading -> error("Not supported")

        }
    }


    class ProgressViewHolder internal constructor(
        private val binding: ItemProgressBinding
    ): ItemViewHolder(binding.root){

        override fun bind(state :LoadState) {
            // do nothing
        }

        companion object {

            operator fun invoke(
                layoutInflater :LayoutInflater,
                parent :ViewGroup? = null,
                attachToRoot: Boolean = false
            ): ProgressViewHolder {
               return ProgressViewHolder(
                   ItemProgressBinding.inflate(
                       layoutInflater, parent, attachToRoot
                   )
               )
            }
        }
    }


}