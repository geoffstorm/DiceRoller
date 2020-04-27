package com.gstormdev.diceroller.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gstormdev.diceroller.interfaces.DieRoller
import com.gstormdev.diceroller.interfaces.RollResult
import com.gstormdev.diceroller.util.DiceNotationCombinedLiveData

class HomeViewModel(private var dieRoller: DieRoller) : ViewModel() {

    val numberOfDice = MutableLiveData<Int?>(1)
    val numberOfSides = MutableLiveData<Int?>(6)
    val constant = MutableLiveData<Int?>(0)

    val diceNotation = DiceNotationCombinedLiveData(numberOfDice, numberOfSides, constant)

    val result = MutableLiveData<RollResult>()

    fun rollDice() {
        result.value = dieRoller.rollDie(numberOfDice.value ?: 0, numberOfSides.value ?: 0, constant.value ?: 0)
    }
}