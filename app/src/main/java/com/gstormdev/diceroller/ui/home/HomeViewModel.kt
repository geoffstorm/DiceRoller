package com.gstormdev.diceroller.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gstormdev.diceroller.interfaces.DieRoller
import com.gstormdev.diceroller.services.StandardDieRoller
import com.gstormdev.diceroller.util.DiceNotationCombinedLiveData

class HomeViewModel : ViewModel() {

    val numberOfDice = MutableLiveData(1)
    val numberOfSides = MutableLiveData(6)
    val constant = MutableLiveData(0)

    val diceNotation = DiceNotationCombinedLiveData(numberOfDice, numberOfSides, constant)

    val dieRoller: DieRoller by lazy { StandardDieRoller() }

    fun rollDice() {
        Log.e("HomeViewModel", "${numberOfDice.value}d${numberOfSides.value}+${constant.value}")
        val result = dieRoller.rollDie(numberOfDice.value ?: 0, numberOfSides.value ?: 0, constant.value ?: 0)
    }
}