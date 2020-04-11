package com.gstormdev.diceroller.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gstormdev.diceroller.services.StandardDieRoller
import com.gstormdev.diceroller.ui.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(StandardDieRoller())
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}