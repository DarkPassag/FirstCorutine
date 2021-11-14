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
import com.ch.ni.an.invest.model.AnimeChan
import com.ch.ni.an.invest.model.FavouriteAnimeChan
import com.ch.ni.an.invest.utills.*
import com.ch.ni.an.invest.viewmodels.StartViewModel

class FragmentStart : Fragment() {

    private val myModel :StartViewModel by activityViewModels()

    private var _bind :FragmentStartBinding? = null
    private val bind :FragmentStartBinding
        get() = _bind!!

    private lateinit var item :AnimeChan
    private lateinit var newItem :FavouriteAnimeChan


    override fun onCreateView(
        inflater :LayoutInflater, container :ViewGroup?, savedInstanceState :Bundle?,
    ) :View {
        _bind = FragmentStartBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view :View, savedInstanceState :Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation()
        getFirstQuote()
        updateUi()

        myModel.favourite.observe(viewLifecycleOwner, {
            if (!it) bind.favouriteButton.setImageResource(R.drawable.ic_no_favourite_24)
            else bind.favouriteButton.setImageResource(R.drawable.ic_favorite_24)
        })
        bind.favouriteButton.setOnClickListener {
            myModel.favouriteButton(newItem)
        }
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
        _bind = null
    }

    private fun updateUi(){
        myModel.loadFavouriteQuotes()
        myModel.checkInRoom(newItem)
    }

    private fun getFirstQuote() {
        val sharedPreference = activity?.applicationContext?.getSharedPreferences(FIRST_LAUNCH, 0)
        sharedPreference?.let {
            item = AnimeChan(it.getString(ANIME, "[EMPTY]")!!,
                it.getString(CHARACTER, "[EMPTY]")!!,
                it.getString(QUOTE, "[EMPTY]")!!)
            newItem = FavouriteAnimeChan(anime = item.anime,
                character = item.character,
                quote = item.quote)
            myModel.checkInRoom(newItem)
            bind.quoteByCharacterTextView.text = item.quote
            bind.characterNameTextView.text = item.character
        }
    }

    private fun navigation() {
        bind.searchByNameButton.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentStart_to_FragmentListTitleAnime,
                bundleOf(SEARCH to SEARCH_BY_CHARACTER))
        }
        bind.searchByTitleButton.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentStart_to_FragmentListTitleAnime,
                bundleOf(SEARCH to SEARCH_BY_TITLE))
        }
        bind.myQuotesButton.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentStart_to_FragmentFavouriteQuotes)
        }
        bind.randomQuotesButton.setOnClickListener {
            findNavController().navigate(R.id.action_FragmentStart_to_FragmentTenRandomQuotes)
        }
    }


}