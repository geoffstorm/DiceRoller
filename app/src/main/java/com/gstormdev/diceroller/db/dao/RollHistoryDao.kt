package com.gstormdev.diceroller.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gstormdev.diceroller.db.entity.RollHistory

@Dao
interface RollHistoryDao {

    @Query("SELECT * FROM rollhistory")
    fun getAll(): LiveData<List<RollHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoll(rollHistory: RollHistory)
}