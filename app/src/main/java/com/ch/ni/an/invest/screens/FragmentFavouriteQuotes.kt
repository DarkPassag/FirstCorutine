package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.BaseFragment
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.adapters.FavouriteQuotesAdapter
import com.ch.ni.an.invest.databinding.FragmentMyQuotesBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.utills.SwipeListenerDelete
import com.ch.ni.an.invest.utills.SwipeToDeleteCallback
import com.ch.ni.an.invest.viewmodels.MyQuotesViewModel

class FragmentFavouriteQuotes: BaseFragment(), SwipeListenerDelete {

    private var _bind :FragmentMyQuotesBinding? = null
    private val bind :FragmentMyQuotesBinding
        get() = _bind!!
    private val myModel :MyQuotesViewModel by activityViewModels()
    private lateinit var recyclerView :RecyclerView
    private lateinit var adapter :FavouriteQuotesAdapter

    override fun onCreateOptionsMenu(menu :Menu, inflater :MenuInflater) {
        inflater.inflate(R.menu.menu_favourite_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item :MenuItem) :Boolean {
        if (item.itemId == R.id.listAvailableAnime) {
            findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item)

    }


    override fun onCreateView(
        inflater :LayoutInflater, container :ViewGroup?, savedInstanceState :Bundle?) :View {
        _bind = FragmentMyQuotesBinding.inflate(inflater, container, false)
        recyclerView = bind.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FavouriteQuotesAdapter(this)
        recyclerView.adapter = adapter
        activity?.window?.setBackgroundDrawableResource(R.drawable.gradient_2)
        return bind.root
    }

    override fun onViewCreated(view :View, savedInstanceState :Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myModel.myQuotes.observe(viewLifecycleOwner, {
            adapter.setList = it.reversed()
            recyclerView.adapter = adapter
            val swipeToDelete = object : SwipeToDeleteCallback() {
                override fun onSwiped(viewHolder :RecyclerView.ViewHolder, direction :Int) {
                    adapter.delete(viewHolder.adapterPosition)
                }
            }
            val touchHelper = ItemTouchHelper(swipeToDelete)
            touchHelper.attachToRecyclerView(recyclerView)
        })
    }

    override fun deleteQuote(quote :AnimeChan) {
        myModel.deleteQuote(quote)
    }


}