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
        val numberOfDice = this.numberOfDice ?: 0
        val numberOfSides = this.numberOfSides ?: 0
        val constant = this.constant ?: 0

        var notation: String = ""

        if (numberOfDice > 0) {
            notation += numberOfDice
        }
        notation += "d$numberOfSides"
        if (constant > 0) {
            notation += "+$constant"
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