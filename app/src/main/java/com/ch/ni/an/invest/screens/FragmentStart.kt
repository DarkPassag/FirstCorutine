package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.ListAnimeAdapter
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.FragmentStartBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.retrofit.AnimeViewModel
import com.ch.ni.an.invest.model.retrofit.STATE.*


class FragmentStart: Fragment(), SearchView.OnQueryTextListener {
    private var _bind: FragmentStartBinding? = null
    private val bind: FragmentStartBinding
        get() = _bind!!
    private val myModel: AnimeViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAnimeAdapter

    override fun onCreate(savedInstanceState :Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu :Menu, inflater :MenuInflater) {
        inflater.inflate(R.menu.menu_list_anime_fragment, menu)
        val search = menu.findItem(R.id.searchIcon).actionView as SearchView
        search.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onQueryTextSubmit(query :String?) :Boolean {
        if(query != null){
            myModel.tempList.clear()
            myModel.search(query)
        } else {
            myModel.getAvailableAnimeList()
        }
        return true
    }

    override fun onQueryTextChange(newText :String?) :Boolean {
        if(newText != null){
            myModel.tempList.clear()
            myModel.search(newText)
        } else {
            myModel.getAvailableAnimeList()
        }
        return true
    }

    override fun onOptionsItemSelected(item :MenuItem) :Boolean {
        if(item.itemId == R.id.favouriteQuotes){
            findNavController().navigate(R.id.action_fragmentStart_to_fragmentMyQuotes)
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentStartBinding.inflate(inflater, container, false)

        recyclerView = bind.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListAnimeAdapter(
            object : RecyclerViewClickListener{
                override fun clickListener(animeName :String) {
                    myModel.getQuotesByAnime(animeName)
                    findNavController().navigate(R.id.action_fragmentStart_to_fragmentAnimeNameQuotes)
                }

                override fun addQuote(animeChan :AnimeChan) {}

            }
        )
        recyclerView.adapter = adapter
        activity?.window?.setBackgroundDrawableResource(R.drawable.gradient_1)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myModel.listAvailableAnime.observe(viewLifecycleOwner, {
            adapter.listAnime = it
            recyclerView.adapter = adapter
        })
        myModel.state.observe(viewLifecycleOwner, {
            when(it){
                PENDING ->{ pendingUI() }
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