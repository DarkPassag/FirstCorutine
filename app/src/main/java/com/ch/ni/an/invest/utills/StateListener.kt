package com.ch.ni.an.invest.utills

import com.ch.ni.an.invest.viewmodels.STATE

interface StateListener {

   operator fun invoke(state: STATE)
}