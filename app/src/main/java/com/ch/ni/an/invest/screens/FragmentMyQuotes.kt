package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.databinding.FragmentMyQuotesBinding

class FragmentMyQuotes: Fragment() {

    private var _bind: FragmentMyQuotesBinding? = null
    private val bind: FragmentMyQuotesBinding
        get() = _bind!!
    private val myModel: MyQuotesViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnimeStartAdapter


    override fun onCreateView(
        inflater :LayoutInflater,
        container :ViewGroup?,
        savedInstanceState :Bundle?) :View {
        _bind = FragmentMyQuotesBinding.inflate(inflater, container, false)
        recyclerView = bind.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AnimeStartAdapter()
        recyclerView.adapter = adapter

        return bind.root
    }

    override fun onViewCreated(view :View, savedInstanceState :Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myModel.myQuotes.observe(viewLifecycleOwner, {
            adapter.randomQuotes = it
            recyclerView.adapter = adapter
        })
    }

}