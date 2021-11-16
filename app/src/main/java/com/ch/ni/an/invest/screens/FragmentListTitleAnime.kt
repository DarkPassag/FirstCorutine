package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.BaseFragment
import com.ch.ni.an.invest.adapters.ListAnimeTitleAdapter
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.FragmentListTitleNameBinding
import com.ch.ni.an.invest.model.FavouriteAnimeChan
import com.ch.ni.an.invest.viewmodels.AnimeViewModel
import com.ch.ni.an.invest.viewmodels.STATE.*
import com.ch.ni.an.invest.utills.RecyclerViewClickListener
import com.ch.ni.an.invest.utills.SEARCH
import com.ch.ni.an.invest.utills.SEARCH_BY_CHARACTER
import com.ch.ni.an.invest.utills.SEARCH_BY_TITLE
import kotlinx.coroutines.launch


class FragmentListTitleAnime: BaseFragment(), SearchView.OnQueryTextListener, RecyclerViewClickListener {

    private var _bind :FragmentListTitleNameBinding? = null
    private val bind :FragmentListTitleNameBinding
        get() = _bind!!

    private val myModel :AnimeViewModel by activityViewModels()
    private lateinit var recyclerView :RecyclerView
    private lateinit var titleAdapter :ListAnimeTitleAdapter
    private lateinit var key :String


    override fun onCreateOptionsMenu(menu :Menu, inflater :MenuInflater) {
        inflater.inflate(R.menu.menu_list_anime_fragment, menu)
        val search = menu.findItem(R.id.searchIcon).actionView as SearchView
        search.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query :String?) :Boolean {
        if (query != null) {
            myModel.tempList.clear()
            myModel.search(query, key)
        } else {
            myModel.getAvailableAnimeList()
            myModel.getCharacters()
        }
        return true
    }

    override fun onQueryTextChange(newText :String?) :Boolean {
        if (newText != null) {
            myModel.tempList.clear()
            myModel.search(newText, key)
        } else {
            myModel.getAvailableAnimeList()
            myModel.getCharacters()
        }
        return true
    }

    override fun onOptionsItemSelected(item :MenuItem) :Boolean {
        if (item.itemId == R.id.favouriteQuotes) {
            findNavController().navigate(R.id.action_FragmentListTitleAnime_to_FragmentFavouriteQuotes)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateView(
        inflater :LayoutInflater, container :ViewGroup?, savedInstanceState :Bundle?,
    ) :View {
        _bind = FragmentListTitleNameBinding.inflate(inflater, container, false)

        recyclerView = bind.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        titleAdapter = ListAnimeTitleAdapter(this)
        recyclerView.adapter = titleAdapter
        activity?.window?.setBackgroundDrawableResource(R.drawable.gradient_1)
        return bind.root
    }

    override fun onViewCreated(view :View, savedInstanceState :Bundle?) {
        key = requireArguments().getString(SEARCH, SEARCH_BY_TITLE)
        when (key) {
            SEARCH_BY_TITLE -> {
                myModel.listAvailableAnime.observe(viewLifecycleOwner, {
                    titleAdapter.listAnime = it
                    recyclerView.adapter = titleAdapter
                })
            }
            SEARCH_BY_CHARACTER -> {
                myModel.allNames.observe(viewLifecycleOwner, {
                    titleAdapter.listAnime = it
                    recyclerView.adapter = titleAdapter
                })
            }
        }

        myModel.state.observe(viewLifecycleOwner, {
            when (it) {
                PENDING -> pendingUI()
                SUCCESS -> updateUI()
                FAIL -> failUi()
            }
        })

    }



    private fun updateUI() {
        bind.dotsLoaderProgressbar.visibility = View.GONE
        bind.recyclerView.visibility = View.VISIBLE
    }

    private fun failUi(){
        updateUI()
        if(isOnline(requireContext())){

        } else Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show()
    }


    private fun pendingUI() {
        bind.dotsLoaderProgressbar.visibility = View.VISIBLE
        bind.recyclerView.visibility = View.GONE
    }

    override fun clickListener(anime :String) {
        if (key == SEARCH_BY_TITLE) {
            myModel.character.postValue(anime)
            findNavController().navigate(R.id.action_FragmentListTitleAnime_to_FragmentQuotesByAnimeTitle)
        } else {
            myModel.character.postValue(anime)
            findNavController().navigate(R.id.action_FragmentListTitleAnime_to_FragmentQuotesByAnimeCharacter)
        }

    }

    override fun addQuote(animeChan :FavouriteAnimeChan) {}

    override fun deleteQuote(animeChan :FavouriteAnimeChan) {}

}