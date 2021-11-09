package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.BaseFragment
import com.ch.ni.an.invest.adapters.QuoteByAnimeCharacterAdapter
import com.ch.ni.an.invest.databinding.FragmentAnimecharacterQuotesBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.viewmodels.AnimeViewModel
import com.ch.ni.an.invest.viewmodels.STATE
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.LoadImage
import com.ch.ni.an.invest.utills.RecyclerViewClickListener
import com.ch.ni.an.invest.viewmodels.MyQuotesViewModel

class FragmentQuotesByAnimeCharacter: BaseFragment(), LoadImage, FavouriteCallback, RecyclerViewClickListener {

    private val myModel :AnimeViewModel by activityViewModels()
    private val mModel :MyQuotesViewModel by activityViewModels()

    private var _bind :FragmentAnimecharacterQuotesBinding? = null
    private val bind :FragmentAnimecharacterQuotesBinding
        get() = _bind!!

    private lateinit var recyclerView :RecyclerView
    private lateinit var adapter :QuoteByAnimeCharacterAdapter

    override fun onCreateView(
        inflater :LayoutInflater,
        container :ViewGroup?,
        savedInstanceState :Bundle?) :View {
        _bind = FragmentAnimecharacterQuotesBinding.inflate(inflater, container, false)

        recyclerView = bind.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = QuoteByAnimeCharacterAdapter(this, this, this)
        recyclerView.adapter = adapter

        return bind.root
    }

    override fun onViewCreated(view :View, savedInstanceState :Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mModel.myQuotes.observe(viewLifecycleOwner, {})

        myModel.apply {
            state.observe(viewLifecycleOwner, {
                when (it) {
                    STATE.PENDING -> pendingUI()
                    STATE.SUCCESS -> updateUI()
                    STATE.FAIL -> updateUI()
                }
            })
            quotesByCharacter.observe(viewLifecycleOwner, {
                adapter.setList = it
                recyclerView.adapter = adapter
            })

        }
    }

    private fun updateUI() {
        bind.dotsLoaderProgressbar.visibility = View.GONE
        bind.recyclerView.visibility = View.VISIBLE

    }

    private fun pendingUI() {
        bind.dotsLoaderProgressbar.visibility = View.VISIBLE
        bind.recyclerView.visibility = View.GONE
    }

    override suspend fun loadImage(characterName :String) :String {
        return myModel.getUrlForLoad(characterName)
    }

    override fun checkInRoom(quote :AnimeChan) :Boolean {
        mModel.loadListFavouriteQuote()
        return mModel.checkQuote(quote)
    }

    override fun clickListener(animeName :String) {}

    override fun addQuote(animeChan :AnimeChan) {
        myModel.addQuote(animeChan)
    }

    override fun deleteQuote(animeChan :AnimeChan) {
        myModel.deleteQuote(animeChan)
    }

}