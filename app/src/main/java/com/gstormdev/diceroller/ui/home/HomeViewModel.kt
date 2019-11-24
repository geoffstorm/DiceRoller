package com.gstormdev.diceroller.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val numberOfDice = MutableLiveData(1)
    val numberOfSides = MutableLiveData(6)
    val constant = MutableLiveData(0)

    fun rollDice() {
        Log.e("HomeViewModel", "${numberOfDice.value}d${numberOfSides.value}+${constant.value}")
    }
}