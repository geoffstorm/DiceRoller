package com.gstormdev.diceroller.interfaces

interface DieRoller {
    fun rollDie(numberOfDice: Int, numberOfSides: Int, constant: Int): RollResult
}

data class RollResult(val rolls: List<Int>, val result: Int)