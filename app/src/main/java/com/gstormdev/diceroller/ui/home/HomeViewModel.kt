package com.gstormdev.diceroller.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gstormdev.diceroller.db.entity.RollHistory
import com.gstormdev.diceroller.db.entity.RollParameters
import com.gstormdev.diceroller.db.repository.RollHistoryRepository
import com.gstormdev.diceroller.interfaces.DieRoller
import com.gstormdev.diceroller.interfaces.RollResult
import com.gstormdev.diceroller.util.DiceNotationCombinedLiveData
import kotlinx.coroutines.launch

class HomeViewModel(private var dieRoller: DieRoller, private val rollHistoryRepository: RollHistoryRepository) : ViewModel() {

    val numberOfDice = MutableLiveData<Int?>(1)
    val numberOfSides = MutableLiveData<Int?>(6)
    val constant = MutableLiveData<Int?>(0)

    val diceNotation = DiceNotationCombinedLiveData(numberOfDice, numberOfSides, constant)

    val result = MutableLiveData<RollResult>()

    fun rollDice() {
        val numberOfDice = this.numberOfDice.value ?: 0
        val numberOfSides = this.numberOfSides.value ?: 0
        val constant = this.constant.value ?: 0
        val rollResult = dieRoller.rollDie(numberOfDice, numberOfSides, constant)
        result.value = rollResult
        val rollParameters = RollParameters(numberOfDice, numberOfSides, constant)
        viewModelScope.launch {
            rollHistoryRepository.addHistoryItem(RollHistory(rollParameters, rollResult))
        }
    }
}