package com.ch.ni.an.invest.screens

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
        return bind.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        myModel.listAvailableANime.observe(viewLifecycleOwner, {
            it?.let {
                adapter.listAnime = it
                recyclerView.adapter = adapter
            }

        })



    }
}