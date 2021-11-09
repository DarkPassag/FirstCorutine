package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.BaseFragment
import com.ch.ni.an.invest.adapters.QuoteByAnimeTitleAdapter
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.FragmentAnimenameQuotesBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.viewmodels.AnimeViewModel
import com.ch.ni.an.invest.viewmodels.STATE.*
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.LoadImage
import com.ch.ni.an.invest.utills.RecyclerViewClickListener
import com.ch.ni.an.invest.viewmodels.MyQuotesViewModel

class FragmentQuotesByAnimeTitle : BaseFragment(), RecyclerViewClickListener, FavouriteCallback,
    LoadImage {

    private var _bind :FragmentAnimenameQuotesBinding? = null
    private val bind :FragmentAnimenameQuotesBinding
        get() = _bind!!
    private lateinit var recyclerView :RecyclerView
    private lateinit var titleAdapterQuoteBy :QuoteByAnimeTitleAdapter
    private val myModel :AnimeViewModel by activityViewModels()
    private val mModel :MyQuotesViewModel by activityViewModels()

    override fun onCreateOptionsMenu(menu :Menu, inflater :MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_without_search, menu)
    }

    override fun onOptionsItemSelected(item :MenuItem) :Boolean {
        when (item.itemId) {
            R.id.favouriteQuotes -> {
                findNavController().navigate(
                    R.id.action_FragmentQuotesByAnimeTitle_to_FragmentFavouriteQuotes
                )
            }
            R.id.listAvailableAnime -> {
                findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater :LayoutInflater, container :ViewGroup?, savedInstanceState :Bundle?) :View {
        _bind = FragmentAnimenameQuotesBinding.inflate(inflater, container, false)

        recyclerView = bind.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        titleAdapterQuoteBy = QuoteByAnimeTitleAdapter(this, this, this)
        recyclerView.adapter = titleAdapterQuoteBy
        recyclerView
        return bind.root
    }

    override fun onViewCreated(view :View, savedInstanceState :Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mModel.myQuotes.observe(viewLifecycleOwner, {})

        myModel.apply {
            state.observe(viewLifecycleOwner, {
                when (it) {
                    PENDING -> pendingUI()
                    SUCCESS -> updateUI()
                    FAIL -> updateUI()
                }
            })
            quotesByTitle.observe(viewLifecycleOwner, {
                titleAdapterQuoteBy.animeList = it
                recyclerView.adapter = titleAdapterQuoteBy
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

    override fun checkInRoom(quote :AnimeChan) :Boolean {
        mModel.loadListFavouriteQuote()
        return mModel.checkQuote(quote)
    }

    override fun clickListener(animeName :String) {
        myModel.getQuotesByAnimeCharacter(animeName)
        findNavController().navigate(
            R.id.action_FragmentQuotesByAnimeTitle_to_FragmentQuotesByAnimeCharacter
        )
    }

    override fun addQuote(animeChan :AnimeChan) {
        myModel.addQuote(animeChan)
    }

    override fun deleteQuote(animeChan :AnimeChan) {
        myModel.deleteQuote(animeChan)
    }

    override suspend fun loadImage(characterName :String) :String {
        return myModel.getUrlForLoad(characterName)
    }


}