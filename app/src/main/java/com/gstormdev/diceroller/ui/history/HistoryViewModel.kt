package com.gstormdev.diceroller.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gstormdev.diceroller.db.entity.RollHistory
import com.gstormdev.diceroller.db.repository.RollHistoryRepository

class HistoryViewModel(rollHistoryRepository: RollHistoryRepository) : ViewModel() {

    val rolls: LiveData<List<RollHistory>> = rollHistoryRepository.fetchRollHistory()

    // TODO empty state
}