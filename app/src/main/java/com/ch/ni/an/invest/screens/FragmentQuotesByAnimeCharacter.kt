package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.BaseFragment
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.adapters.QuoteByAnimeCharacterAdapter
import com.ch.ni.an.invest.databinding.FragmentAnimecharacterQuotesBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.viewmodels.AnimeViewModel
import com.ch.ni.an.invest.viewmodels.STATE
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.LoadImage
import com.ch.ni.an.invest.utills.RecyclerViewClickListener
import com.ch.ni.an.invest.viewmodels.MyQuotesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentQuotesByAnimeCharacter: BaseFragment(), LoadImage, FavouriteCallback, RecyclerViewClickListener {

    private val myModel :AnimeViewModel by activityViewModels()
    private val mModel :MyQuotesViewModel by activityViewModels()

    private var _bind :FragmentAnimecharacterQuotesBinding? = null
    private val bind :FragmentAnimecharacterQuotesBinding
        get() = _bind!!

    private lateinit var recyclerView :RecyclerView
    private lateinit var adapter :QuoteByAnimeCharacterAdapter

    override fun onCreateOptionsMenu(menu :Menu, inflater :MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_without_search, menu)
    }


    override fun onOptionsItemSelected(item :MenuItem) :Boolean {
        when (item.itemId) {
            R.id.favouriteQuotes -> {
                findNavController().navigate(
                    R.id.action_FragmentQuotesByAnimeCharacter_to_FragmentFavouriteQuotes
                )
            }

            R.id.homeFragment -> {
                findNavController().navigate(
                    R.id.action_FragmentQuotesByAnimeCharacter_to_FragmentStart
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

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

        myModel.state.observe(viewLifecycleOwner, {
                when (it) {
                    STATE.PENDING -> pendingUI()
                    STATE.SUCCESS -> updateUI()
                    STATE.FAIL -> updateUI()
                }
            })

        lifecycleScope.launch {
            myModel.getQuotesByAnimeCharacter("Madara Uchiha").collectLatest {
                adapter.submitData(it)
                recyclerView.adapter = adapter
            }
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