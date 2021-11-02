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
import com.ch.ni.an.invest.databinding.FragmentAnimeTenRandomQuotesBinding

import com.ch.ni.an.invest.retrofit.AnimeViewModel
import com.ch.ni.an.invest.retrofit.STATE

class FragmentAnimeStart: Fragment() {

    private val myModel: AnimeViewModel by activityViewModels()
    private var _bind: FragmentAnimeTenRandomQuotesBinding? = null
    private val bind: FragmentAnimeTenRandomQuotesBinding
        get() = _bind!!

    private lateinit var adapter : AnimeStartAdapter
    private lateinit var recycleView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentAnimeTenRandomQuotesBinding.inflate(inflater, container, false)
        recycleView = bind.recyclerView
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AnimeStartAdapter()
        recycleView.adapter = adapter


        return bind.root
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

        myModel.randomQuotes.observe(viewLifecycleOwner, {
            adapter.randomQuotes = it
            recycleView.adapter = adapter
        })

        bind.updateFAB.setOnClickListener {
            myModel.getRandomQuotes()
            it.animate().apply {
                rotationBy(360f)
                duration = 1000

                start()
            }
        }
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