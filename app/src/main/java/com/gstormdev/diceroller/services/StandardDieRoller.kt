package com.gstormdev.diceroller.services

import com.gstormdev.diceroller.interfaces.DieRoller
import com.gstormdev.diceroller.interfaces.RollResult
import kotlin.random.Random

class StandardDieRoller: DieRoller {
    override fun rollDie(numberOfDice: Int, numberOfSides: Int, constant: Int): RollResult {
        if (numberOfDice <= 0 || numberOfSides <= 0) {
            return RollResult(emptyList(), if (constant >= 0) constant else 0)
        }

        val rolls = (1..numberOfDice).map { Random.nextInt(1, numberOfSides + 1) }
        val result = rolls.sum() + constant

        return RollResult(rolls, result)
    }
}