package com.ch.ni.an.invest.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ch.ni.an.invest.databinding.FragmentStartBinding


class FragmentStart: Fragment() {
    private val myModel: InvestViewModel by activityViewModels()

    private var _bind: FragmentStartBinding? = null
    private lateinit var bind: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentStartBinding.inflate(inflater)
        bind= _bind!!
        return bind.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myModel.changeStatus()
        myModel.status.observe(viewLifecycleOwner, { status ->
            when(status){
                STATUS.PENDING -> {
                    bind.progressBar.visibility = View.VISIBLE
                    bind.viewView.visibility = View.GONE
                }
                STATUS.ERROR -> {
                    bind.viewView.visibility = View.VISIBLE
                    bind.progressBar.visibility = View.GONE
                }
                STATUS.OK -> {
                    bind.viewView.visibility = View.VISIBLE
                    bind.progressBar.visibility = View.GONE
                }
            }
        })
        myModel.text.observe(viewLifecycleOwner, {
            bind.textTextView.text = it
            bind.colorButton.visibility = View.VISIBLE
        })
        myModel.color.observe(viewLifecycleOwner,{
            bind.colorButton.background.setTint(Color.rgb(it.red, it.green, it.black))
        })
        bind.startTaskButton.setOnClickListener {
            myModel.changeText()
        }
        bind.changeColorButton.setOnClickListener {
            myModel.changeColor()
            bind.colorButton.visibility =View.VISIBLE
        }
        bind.asyncChangeTextAndColorButton.setOnClickListener {
            myModel.asyncChangeTextAndColor()
        }
    }
}