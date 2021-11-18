package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.request.Disposable
import com.ch.ni.an.invest.BaseFragment
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.adapters.AnimeStateAdapter
import com.ch.ni.an.invest.adapters.QuoteByAnimeCharacterAdapter
import com.ch.ni.an.invest.databinding.FragmentAnimecharacterQuotesBinding
import com.ch.ni.an.invest.model.FavouriteAnimeChan
import com.ch.ni.an.invest.viewmodels.AnimeViewModel
import com.ch.ni.an.invest.viewmodels.STATE
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.LoadImage
import com.ch.ni.an.invest.utills.RecyclerViewClickListener
import com.ch.ni.an.invest.viewmodels.MyQuotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentQuotesByAnimeCharacter : BaseFragment(), LoadImage, FavouriteCallback,
    RecyclerViewClickListener {

    private val myModel :AnimeViewModel by activityViewModels()
    private val mModel :MyQuotesViewModel by activityViewModels()

    private var _bind :FragmentAnimecharacterQuotesBinding? = null
    private val bind :FragmentAnimecharacterQuotesBinding
        get() = _bind!!

    private lateinit var recyclerView :RecyclerView
    private lateinit var adapter :QuoteByAnimeCharacterAdapter
    private lateinit var character :String

    override fun onCreateOptionsMenu(menu :Menu, inflater :MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_without_search, menu)
    }


    override fun onOptionsItemSelected(item :MenuItem) :Boolean {
        when (item.itemId) {
            R.id.favouriteQuotes -> {
                findNavController().navigate(R.id.action_FragmentQuotesByAnimeCharacter_to_FragmentFavouriteQuotes)
            }

            R.id.homeFragment -> {
                findNavController().navigate(R.id.action_FragmentQuotesByAnimeCharacter_to_FragmentStart)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater :LayoutInflater,
        container :ViewGroup?,
        savedInstanceState :Bundle?,
    ) :View {
        _bind = FragmentAnimecharacterQuotesBinding.inflate(inflater, container, false)

        recyclerView = bind.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = QuoteByAnimeCharacterAdapter(this, this, this)
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = AnimeStateAdapter(),
            footer = AnimeStateAdapter()
        )

        return bind.root
    }

    override fun onViewCreated(view :View, savedInstanceState :Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mModel.myQuotes.observe(viewLifecycleOwner, {})

        myModel.character.observe(viewLifecycleOwner, {
            character = it

        })

        myModel.state.observe(viewLifecycleOwner, {
            when (it) {
                STATE.PENDING -> pendingUI()
                STATE.SUCCESS -> updateUI()
                STATE.FAIL -> failUi()
            }
        })




        lifecycleScope.launchWhenResumed(){
            myModel.getQuotesByAnimeCharacter(character).collectLatest {
                it.also {
                    adapter.submitData(it)

                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bind = null
    }

    private fun updateUI() {
        bind.dotsLoaderProgressbar.visibility = View.GONE
        bind.recyclerView.visibility = View.VISIBLE
    }

    private fun failUi(){
        updateUI()
        Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show()
    }

    private fun pendingUI() {
        bind.dotsLoaderProgressbar.visibility = View.VISIBLE
        bind.recyclerView.visibility = View.GONE
    }

    override suspend fun loadImage(characterName :String) :String {
        return myModel.getUrlForLoad(characterName)
    }

    override fun checkInRoom(quote :FavouriteAnimeChan) :Boolean {
        mModel.loadFavouriteQuotes()
        return mModel.checkQuote(quote)
    }

    override fun clickListener(anime :String) {}

    override fun addQuote(animeChan :FavouriteAnimeChan) {
        myModel.addQuote(animeChan)
    }

    override fun deleteQuote(animeChan :FavouriteAnimeChan) {
        myModel.deleteQuote1(animeChan)
    }



}