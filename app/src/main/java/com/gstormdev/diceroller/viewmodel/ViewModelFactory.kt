package com.gstormdev.diceroller.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gstormdev.diceroller.db.AppDatabase
import com.gstormdev.diceroller.db.repository.RollHistoryRepository
import com.gstormdev.diceroller.services.StandardDieRoller
import com.gstormdev.diceroller.ui.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val applicationContext: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(StandardDieRoller(),
                        RollHistoryRepository(AppDatabase.getDatabase(applicationContext).rollHistoryDao()))
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}