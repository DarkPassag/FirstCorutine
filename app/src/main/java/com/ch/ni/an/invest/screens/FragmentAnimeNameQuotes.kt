package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.AnimeAdapter
import com.ch.ni.an.invest.databinding.FragmentAnimenameQuotesBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.retrofit.AnimeViewModel
import com.ch.ni.an.invest.model.retrofit.STATE.*
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.RecyclerViewClickListener
import com.ch.ni.an.invest.viewmodels.MyQuotesViewModel

class FragmentAnimeNameQuotes:Fragment() {

    private var _bind: FragmentAnimenameQuotesBinding? = null
    private val bind: FragmentAnimenameQuotesBinding
        get() = _bind!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnimeAdapter
    private val myModel: AnimeViewModel by activityViewModels()
    private val mModel: MyQuotesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentAnimenameQuotesBinding.inflate(inflater, container, false)

        recyclerView = bind.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AnimeAdapter(object : RecyclerViewClickListener {
            override fun clickListener(animeName: String) {
            }

            override fun addQuote(animeChan :AnimeChan) {
                myModel.addQuote(animeChan)
            }
        }, object : FavouriteCallback{
            override fun checkInRoom(quote :AnimeChan) :Boolean {
                return mModel.checkQuote(quote)
            }


        }
        )
        recyclerView.adapter = adapter
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myModel.quotesByAnimaCharacter.observe(viewLifecycleOwner, {
            adapter.animeList = it
            recyclerView.adapter = adapter
        })
        myModel.state.observe(viewLifecycleOwner, {
            when(it){
                PENDING -> { pendingUI() }
                SUCCESS -> { updateUI() }
                FAIL -> { updateUI() }
            }
        })

    }


    private fun updateUI(){
        bind.dotsLoaderProgressbar.visibility = View.GONE
        bind.recyclerView.visibility = View.VISIBLE

    }

    private fun pendingUI(){
        bind.dotsLoaderProgressbar.visibility = View.VISIBLE
        bind.recyclerView.visibility = View.GONE
    }
}