package com.ch.ni.an.invest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.databinding.FragmentStartBinding
import com.ch.ni.an.invest.databinding.RecyclerviewItemBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.screens.RecyclerViewClickListener

class AnimeAdapter(private val clickListener: RecyclerViewClickListener
): RecyclerView.Adapter<AnimeAdapter.AnimeHolder>() {

    class AnimeHolder( val bind: RecyclerviewItemBinding) : RecyclerView.ViewHolder(bind.root) {

    }
    var animeList: List<AnimeChan> = emptyList()
    set(value) {
        val diffCallback = AnimeDiffUtil(field, value)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        field = value
        diffResult.dispatchUpdatesTo(this)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = RecyclerviewItemBinding.inflate(inflater, parent, false)
        return AnimeHolder(bind)
    }

    override fun onBindViewHolder(holder: AnimeHolder, position: Int) {
        val item = animeList[position]
        holder.bind.animeNameTextView.text = item.anime.toString()
        holder.bind.animeNameTextView.setOnClickListener {
            clickListener.clickListener(animeName = item.anime!!)
        }
    }

    override fun getItemCount(): Int = animeList.size


}
class AnimeDiffUtil(
    private val oldList: List<AnimeChan>,
    private val newList: List<AnimeChan>
): DiffUtil.Callback() {


    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val animeOldItem = oldList[oldItemPosition]
        val animeNewItem = newList[newItemPosition]
        return animeNewItem == animeOldItem
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val animeOldItem = oldList[oldItemPosition]
        val animeNewItem = newList[newItemPosition]
        return animeNewItem == animeOldItem
    }
}