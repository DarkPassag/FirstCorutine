package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.BaseFragment
import com.ch.ni.an.invest.adapters.QuoteByAnimeTitleAdapter
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.adapters.AnimeStateAdapter
import com.ch.ni.an.invest.databinding.FragmentAnimenameQuotesBinding
import com.ch.ni.an.invest.model.FavouriteAnimeChan
import com.ch.ni.an.invest.viewmodels.AnimeViewModel
import com.ch.ni.an.invest.viewmodels.STATE.*
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.LoadImage
import com.ch.ni.an.invest.utills.RecyclerViewClickListener
import com.ch.ni.an.invest.viewmodels.MyQuotesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentQuotesByAnimeTitle : BaseFragment(), RecyclerViewClickListener, FavouriteCallback,
    LoadImage {


    private var _bind :FragmentAnimenameQuotesBinding? = null
    private val bind :FragmentAnimenameQuotesBinding
        get() = _bind!!
    private lateinit var recyclerView :RecyclerView
    private lateinit var adapter :QuoteByAnimeTitleAdapter
    private val myModel :AnimeViewModel by activityViewModels()
    private val mModel :MyQuotesViewModel by activityViewModels()
    private lateinit var title :String

    override fun onCreateOptionsMenu(menu :Menu, inflater :MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_without_search, menu)
    }

    override fun onOptionsItemSelected(item :MenuItem) :Boolean {
        when (item.itemId) {
            R.id.favouriteQuotes -> {
                findNavController().navigate(R.id.action_FragmentQuotesByAnimeTitle_to_FragmentFavouriteQuotes)
            }
            R.id.homeFragment -> {
                findNavController().navigate(R.id.action_FragmentQuotesByAnimeTitle_to_FragmentStart)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater :LayoutInflater, container :ViewGroup?, savedInstanceState :Bundle?,
    ) :View {
        _bind = FragmentAnimenameQuotesBinding.inflate(inflater, container, false)

        recyclerView = bind.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = QuoteByAnimeTitleAdapter(this, this, this)
        recyclerView.adapter = adapter.withLoadStateFooter(AnimeStateAdapter())
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
                    FAIL -> failUi()
                }
            })
        }
        myModel.character.observe(viewLifecycleOwner, {
            title = it
        })

        lifecycleScope.launch {
            delay(500)
            myModel.getQuotesByTitle(title).collectLatest {
                adapter.submitData(it)
            }
        }

    }






    private fun updateUI() {
        bind.dotsLoaderProgressbar.visibility = View.GONE
        bind.recyclerView.visibility = View.VISIBLE
    }
    private fun failUi(){
        updateUI()
        toast()
    }

    private fun pendingUI() {
        bind.dotsLoaderProgressbar.visibility = View.VISIBLE
        bind.recyclerView.visibility = View.GONE
    }

    override fun checkInRoom(quote :FavouriteAnimeChan) :Boolean {
        mModel.loadFavouriteQuotes()
        return mModel.checkQuote(quote)
    }

    override fun clickListener(anime :String) {
        if(isOnline(requireContext())){
            myModel.character.postValue(anime)
            myModel.getQuotesByAnimeCharacter(anime)
            findNavController().navigate(R.id.action_FragmentQuotesByAnimeTitle_to_FragmentQuotesByAnimeCharacter)
        } else toast()

    }

    override fun addQuote(animeChan :FavouriteAnimeChan) {
        myModel.addQuote(animeChan)
    }

    override fun deleteQuote(animeChan :FavouriteAnimeChan) {
        myModel.deleteQuote1(animeChan)
    }

    override suspend fun loadImage(characterName :String) :String {
        return myModel.getUrlForLoad(characterName)
    }

    private fun toast(){
        Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show()
    }


}