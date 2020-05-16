package com.gstormdev.diceroller.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gstormdev.diceroller.interfaces.RollResult
import java.util.UUID

@Entity
data class RollHistory(
        @Embedded val rollParameters: RollParameters,
        @Embedded val rollResult: RollResult,
        @PrimaryKey val id: String = UUID.randomUUID().toString(),
        val rollDateTime: Long = System.currentTimeMillis()
)

data class RollParameters(val dieCount: Int, val sideCount: Int, val constant: Int)