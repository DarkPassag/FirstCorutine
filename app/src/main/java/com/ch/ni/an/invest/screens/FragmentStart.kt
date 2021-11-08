package com.ch.ni.an.invest.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ch.ni.an.invest.R
import com.ch.ni.an.invest.databinding.FragmentStartBinding
import com.ch.ni.an.invest.utills.SEARCH
import com.ch.ni.an.invest.utills.SEARCH_BY_CHARACTER
import com.ch.ni.an.invest.utills.SEARCH_BY_TITLE

class FragmentStart: Fragment() {
    private var _bind: FragmentStartBinding? = null
    private val bind: FragmentStartBinding
        get() = _bind!!


    override fun onCreateView(
        inflater :LayoutInflater,
        container :ViewGroup?,
        savedInstanceState :Bundle?) :View {
        _bind = FragmentStartBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view :View, savedInstanceState :Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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