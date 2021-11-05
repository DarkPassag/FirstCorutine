package com.ch.ni.an.invest

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.databinding.RecyclerviewItemBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.RecyclerViewClickListener

class QuoteByAnimeTitleAdapter(
    private val clickListener:RecyclerViewClickListener,
    private val favouriteCheck: FavouriteCallback
): RecyclerView.Adapter<QuoteByAnimeTitleAdapter.AnimeHolder>() {

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
        holder.bind.characterNameTextView.text = item.character.toString()
        holder.bind.quoteByCharacterTextView.text = item.quote.toString()
        val favouriteButton = holder.bind.favouriteButton
        var flag = chekFlag(item)
            checkFavourite(favouriteButton, item)
        favouriteButton.setOnClickListener{
                if(flag == 0){
                    clickListener.deleteQuote(item)
                    favouriteButton.setImageResource(R.drawable.ic_no_favourite)
                    flag = chekFlag(item)
                } else {
                    clickListener.addQuote(item)
                    favouriteButton.setImageResource(R.drawable.ic_favourite)
                    flag = chekFlag(item)
                }
        }
    }

    override fun getItemCount(): Int = animeList.size



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

    /**
    */

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