package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.FragmentMyQuotesBinding

class FragmentMyQuotes: Fragment() {

    private var _bind: FragmentMyQuotesBinding? = null
    private val bind: FragmentMyQuotesBinding
        get() = _bind!!
    private val myModel: MyQuotesViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnimeStartAdapter

    override fun onCreate(savedInstanceState :Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu :Menu, inflater :MenuInflater) {
        inflater.inflate(R.menu.menu_favourite_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item :MenuItem) :Boolean {
        if(item.itemId == R.id.listAvailableAnime){
            findNavController().navigate(R.id.action_fragmentMyQuotes_to_fragmentStart)
        }
        return super.onOptionsItemSelected(item)

    }


    override fun onCreateView(
        inflater :LayoutInflater,
        container :ViewGroup?,
        savedInstanceState :Bundle?) :View {
        _bind = FragmentMyQuotesBinding.inflate(inflater, container, false)
        recyclerView = bind.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AnimeStartAdapter()
        recyclerView.adapter = adapter
        activity?.window?.setBackgroundDrawableResource(R.drawable.gradient_2)
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