package com.ch.ni.an.invest.utills

import com.ch.ni.an.invest.model.AnimeChan

interface ListFilterImpl {

    fun filter(list: List<AnimeChan>): List<AnimeChan>
}