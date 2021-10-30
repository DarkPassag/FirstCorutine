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
import com.ch.ni.an.invest.databinding.FragmentAnimeStartBinding
import com.ch.ni.an.invest.ondedayretrofit.AnimeViewModel
import com.ch.ni.an.invest.ondedayretrofit.STATE

class FragmentAnimeStart: Fragment() {

    private val myModel: AnimeViewModel by activityViewModels()
    private lateinit var _bind: FragmentAnimeStartBinding
    private val bind: FragmentAnimeStartBinding
        get() = _bind!!

    private lateinit var adapter : AnimeStartAdapter
    private lateinit var recycleView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentAnimeStartBinding.inflate(inflater, container, false)
        recycleView = bind.recyclerView
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AnimeStartAdapter()
        recycleView.adapter = adapter

        return _bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myModel.state.observe(viewLifecycleOwner, {
            when(it){
                STATE.PENDING -> pending()
                STATE.SUCCESS -> updateUI()
                STATE.FAIL -> {
                    updateUI()
                    Toast.makeText(requireContext(), "Anything error!!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        myModel.animeQuotes.observe(viewLifecycleOwner, {
            adapter.randomQuotes = it
            recycleView.adapter = adapter
        })
    }





    private fun updateUI(){
        bind.dotsLoaderProgressbar.visibility = View.GONE
        bind.recyclerView.visibility = View.VISIBLE
        bind.updateFAB.visibility = View.VISIBLE
    }
    private fun pending(){
        bind.dotsLoaderProgressbar.visibility = View.VISIBLE
        bind.recyclerView.visibility = View.GONE

    }
}