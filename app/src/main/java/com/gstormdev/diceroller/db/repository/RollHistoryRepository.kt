package com.gstormdev.diceroller.db.repository

import androidx.lifecycle.LiveData
import com.gstormdev.diceroller.db.dao.RollHistoryDao
import com.gstormdev.diceroller.db.entity.RollHistory

class RollHistoryRepository(private val rollHistoryDao: RollHistoryDao) {

    fun fetchRollHistory(): LiveData<List<RollHistory>> {
        return rollHistoryDao.getAll()
    }

    suspend fun addHistoryItem(rollHistory: RollHistory) {
        rollHistoryDao.insertRoll(rollHistory)
    }
}