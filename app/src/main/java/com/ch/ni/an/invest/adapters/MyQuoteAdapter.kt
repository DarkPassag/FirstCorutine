package com.ch.ni.an.invest.adapters

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.databinding.MyquoteRecyclerviewBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.utills.SwipeListenerDelete

class MyQuoteAdapter(val swipeListenerDelete :SwipeListenerDelete)
    : RecyclerView.Adapter<MyQuoteAdapter.MyQuoteHolder>() {

    var setList: List<AnimeChan> = emptyList()
        set(value) {
            val diffCall = AnimeQuoteDiffUtil(field, value)
            val result = DiffUtil.calculateDiff(diffCall)
            field = value
            result.dispatchUpdatesTo(this)
        }

    class MyQuoteHolder(
        val bind: MyquoteRecyclerviewBinding
        ): RecyclerView.ViewHolder(bind.root) {

        }

    override fun onCreateViewHolder(parent :ViewGroup, viewType :Int) :MyQuoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = MyquoteRecyclerviewBinding.inflate(inflater, parent, false)
        return MyQuoteHolder(bind)
    }

    override fun onBindViewHolder(holder :MyQuoteHolder, position :Int) {
        val item = setList[position]
        holder.bind.animeNameTextView.text = item.anime
        holder.bind.animeCharacterTextView.text = item.character
        holder.bind.animeQuoteTextView.text = item.quote
    }

    override fun getItemCount() :Int = setList.size

    fun delete(id: Int){
        val oldList = setList.toMutableList()
        val idQuote = setList[id]
        oldList.removeAt(id)
        setList = oldList
        swipeListenerDelete.deleteQuote(idQuote)
    }


}

class AnimeQuoteDiffUtil(
    private val oldList: List<AnimeChan>,
    private val newList: List<AnimeChan>
) : DiffUtil.Callback(){
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