package com.ch.ni.an.invest.screens

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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


class FragmentStart: Fragment() {
    private lateinit var bind: FragmentStartBinding
    private val myModel: AnimeViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentStartBinding.inflate(inflater,container,false)
        recyclerView = bind.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ListAnimeAdapter(object : RecyclerViewClickListener{
            override fun clickListener(animeName: String) {
                myModel.getQuotesByAnime1(animeName)
                findNavController().navigate(R.id.action_fragmentStart_to_fragmentAnimeNameQuotes)
            }
        })
        recyclerView.adapter = adapter
        backgroundAnimation()
        return bind.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        myModel.state.observe(viewLifecycleOwner, {
            when(it){
                STATE.PENDING -> pending()
                STATE.SUCCESS -> updateUI()
                STATE.FAIL -> updateUI()
            }
        })

        myModel.listAvailableANime.observe(viewLifecycleOwner, {
            it?.let {
                adapter.listAnime = it
                recyclerView.adapter = adapter
            }

        })



    }


    private fun backgroundAnimation(){
        val animatorDrawable = bind.recyclerView.background as AnimationDrawable
        animatorDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(3000)
            start()
        }
    }

    private fun updateUI(){
        bind.dotsLoaderProgressbar.visibility = View.GONE
        bind.recyclerView.visibility = View.VISIBLE
    }
    private fun pending(){
        bind.dotsLoaderProgressbar.visibility = View.VISIBLE
        bind.recyclerView.visibility = View.GONE
    }
}