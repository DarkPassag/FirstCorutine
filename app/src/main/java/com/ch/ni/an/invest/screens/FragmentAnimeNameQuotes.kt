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
import com.ch.ni.an.invest.AnimeAdapter
import com.ch.ni.an.invest.databinding.FragmentAnimenameQuotesBinding
import com.ch.ni.an.invest.ondedayretrofit.AnimeViewModel

class FragmentAnimeNameQuotes:Fragment() {

    private var _bind: FragmentAnimenameQuotesBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnimeAdapter
    private val myModel: AnimeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentAnimenameQuotesBinding.inflate(inflater, container, false)
        val bind = _bind!!

        recyclerView = bind.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AnimeAdapter(object : RecyclerViewClickListener{
            override fun clickListener(animeName: String) {
                Toast.makeText(requireContext(), animeName, Toast.LENGTH_LONG).show()
            }
        })
        recyclerView.adapter = adapter
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myModel.animeQuotes.observe(viewLifecycleOwner, {
            adapter.animeList = it
            recyclerView.adapter = adapter
        })
    }
}