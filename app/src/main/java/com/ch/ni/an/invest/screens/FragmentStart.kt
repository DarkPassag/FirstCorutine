package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.FragmentStartBinding
import com.ch.ni.an.invest.model.retrofit.STATE.*
import com.ch.ni.an.invest.utills.SEARCH
import com.ch.ni.an.invest.utills.SEARCH_BY_CHARACTER
import com.ch.ni.an.invest.utills.SEARCH_BY_TITLE
import com.ch.ni.an.invest.viewmodels.StartViewModel

class FragmentStart: Fragment() {

    private val myModel :StartViewModel by activityViewModels()

    private var _bind :FragmentStartBinding? = null
    private val bind :FragmentStartBinding
        get() = _bind!!


    override fun onCreateView(
        inflater :LayoutInflater, container :ViewGroup?, savedInstanceState :Bundle?) :View {
        _bind = FragmentStartBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view :View, savedInstanceState :Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation()
        myModel.getQuote()
        myModel.state.observe(viewLifecycleOwner, {
            when (it) {
                PENDING -> pendingUI()
                SUCCESS -> updateUI()
                FAIL -> updateUI()
            }
        })
        myModel.randomQuote.observe(viewLifecycleOwner, {
            bind.characterNameTextView.text = it.character
            bind.quoteByCharacterTextView.text = it.quote
        })
    }

    private fun updateUI() {
        bind.mainGroup.visibility = View.VISIBLE
        bind.loadingGroup.visibility = View.GONE
    }

    private fun pendingUI() {
        bind.mainGroup.visibility = View.GONE
        bind.loadingGroup.visibility = View.VISIBLE
    }


    private fun navigation() {
        bind.searchByNameButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_FragmentStart_to_FragmentListTitleAnime,
                bundleOf(SEARCH to SEARCH_BY_CHARACTER)
            )
        }
        bind.searchByTitleButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_FragmentStart_to_FragmentListTitleAnime,
                bundleOf(SEARCH to SEARCH_BY_TITLE)
            )
        }
        bind.myQuotesButton.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentStart_to_FragmentFavouriteQuotes)
        }
        bind.randomQuotesButton.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentStart_to_FragmentTenRandomQuotes)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }


    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}