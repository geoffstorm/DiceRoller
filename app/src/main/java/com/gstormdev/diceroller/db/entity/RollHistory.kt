package com.gstormdev.diceroller.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class RollHistory(
        @PrimaryKey val id: String = UUID.randomUUID().toString(),
        val rollDateTime: Long = System.currentTimeMillis(),
        @Embedded val rollParameters: RollParameters,
        @Embedded val rollResult: RollResult
)

data class RollParameters(val dieCount: Int, val sideCount: Int, val constant: Int)
data class RollResult(val rolls: List<Int>, val result: Int)