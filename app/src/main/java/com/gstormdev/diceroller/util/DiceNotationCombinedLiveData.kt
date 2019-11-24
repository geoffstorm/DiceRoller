package com.gstormdev.diceroller.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import java.lang.UnsupportedOperationException

class DiceNotationCombinedLiveData(diceSource: LiveData<Int>, sidesSource: LiveData<Int>, constantSource: LiveData<Int>): MediatorLiveData<String>() {
    private var numberOfDice: Int? = null
    private var numberOfSides: Int? = null
    private var constant: Int? = null

    init {
        super.addSource(diceSource) {
            numberOfDice = it
            value = buildDiceNotation()
        }

        super.addSource(sidesSource) {
            numberOfSides = it
            value = buildDiceNotation()
        }

        super.addSource(constantSource) {
            constant = it
            value = buildDiceNotation()
        }
    }

    private fun buildDiceNotation(): String {
        var notation: String = ""
        numberOfDice?.let {
            if (it > 0) {
                notation += it
            }
        }
        notation += "d"
        numberOfSides?.let { notation += it }
        constant?.let {
            if (it > 0) {
                notation += "+$it"
            }
        }
        return notation
    }

    override fun <S : Any?> addSource(source: LiveData<S>, onChanged: Observer<in S>) {
        throw UnsupportedOperationException()
    }

    override fun <S : Any?> removeSource(toRemote: LiveData<S>) {
        throw UnsupportedOperationException()
    }
}