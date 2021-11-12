package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ch.ni.an.invest.BaseFragment
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.adapters.TenRandomQuotesAdapter
import com.ch.ni.an.invest.databinding.FragmentAnimeTenRandomQuotesBinding
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.FavouriteAnimeChan
import com.ch.ni.an.invest.utills.FavouriteCallback
import com.ch.ni.an.invest.utills.RecyclerViewClickListener

import com.ch.ni.an.invest.viewmodels.AnimeViewModel
import com.ch.ni.an.invest.viewmodels.MyQuotesViewModel
import com.ch.ni.an.invest.viewmodels.STATE

class FragmentTenRandomQuotes: BaseFragment(), RecyclerViewClickListener, FavouriteCallback {

    private val myModel:AnimeViewModel by activityViewModels()
    private val mModel :MyQuotesViewModel by activityViewModels()
    private var _bind: FragmentAnimeTenRandomQuotesBinding? = null
    private val bind: FragmentAnimeTenRandomQuotesBinding
        get() = _bind!!

    private lateinit var adapter :TenRandomQuotesAdapter
    private lateinit var recycleView: RecyclerView


    override fun onCreateOptionsMenu(menu :Menu, inflater :MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater :LayoutInflater, container :ViewGroup?, savedInstanceState :Bundle?
    ) :View {
        _bind = FragmentAnimeTenRandomQuotesBinding.inflate(inflater, container, false)
        recycleView = bind.recyclerView
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        adapter = TenRandomQuotesAdapter(this,this)
        recycleView.adapter = adapter


        return bind.root
    }

    override fun onViewCreated(view :View, savedInstanceState :Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myModel.state.observe(viewLifecycleOwner, {
            when (it) {
                STATE.PENDING -> pending()
                STATE.SUCCESS -> updateUI()
                STATE.FAIL -> {
                    updateUI()
                    Toast.makeText(requireContext(), R.string.no_internet, Toast.LENGTH_SHORT).show()
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

    override fun checkInRoom(quote :FavouriteAnimeChan) :Boolean {
        return mModel.checkQuote(quote)
    }

    override fun clickListener(anime :String) {}

    override fun addQuote(animeChan :FavouriteAnimeChan) {
        myModel.addQuote(animeChan)
    }

    override fun deleteQuote(animeChan :FavouriteAnimeChan) {
        myModel.deleteQuote(animeChan)
    }

}