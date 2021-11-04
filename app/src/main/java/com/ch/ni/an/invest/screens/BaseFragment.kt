package com.ch.ni.an.invest.screens

import android.os.Bundle
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    override fun onCreate(savedInstanceState :Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}