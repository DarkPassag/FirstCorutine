package com.ch.ni.an.invest.utills


import com.ch.ni.an.invest.model.AnimeChan

class ListFilter : ListFilterImpl {

    private val mainList :MutableList<AnimeChan> = mutableListOf()

    override fun filter(list :List<AnimeChan>) :List<AnimeChan> {

        return if (mainList.isEmpty()) {
            addNewList(list)
            list
        } else {
            val tempList :MutableList<AnimeChan> =
                list.filter { !mainList.contains(it) }.toMutableList()
            addNewList(list)
            tempList
        }

    }

    private fun addNewList(quotes :List<AnimeChan>) {
        mainList.addAll(quotes)
    }

    fun clear() {
        mainList.clear()
    }

}