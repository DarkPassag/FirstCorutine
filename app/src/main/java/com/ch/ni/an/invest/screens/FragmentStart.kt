package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.AnimeAdapter
import com.ch.ni.an.invest.ListAnimeAdapter
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.FragmentStartBinding
import com.ch.ni.an.invest.ondedayretrofit.AnimeViewModel
import com.ch.ni.an.invest.ondedayretrofit.STATE
import com.ch.ni.an.invest.ondedayretrofit.STATE.*


class FragmentStart: Fragment() {
    private var _bind: FragmentStartBinding? = null
    private val bind: FragmentStartBinding
        get() = _bind!!
    private val myModel: AnimeViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAnimeAdapter


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

            }
        )
        recyclerView.adapter = adapter

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

        bind.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query :String?) :Boolean {
                return false
            }

            override fun onQueryTextChange(newText :String?) :Boolean {
                if(newText != null){
                        myModel.tempList.clear()
                        myModel.search(newText)
                    } else myModel.getAvailableAnimeList()

                    return false
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