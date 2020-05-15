package com.gstormdev.diceroller.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Formatters {
    fun diceNotationFormatter(numberOfDice: Int, numberOfSides: Int, constant: Int): String {
        var notation = ""

        if (numberOfDice >= 0) {
            notation += numberOfDice
        }
        notation += "d$numberOfSides"
        if (constant > 0) {
            notation += "+$constant"
        }

        return notation
    }

    fun defaultDateFormatter(milliseconds: Long): String {
        return defaultDateFormatter(Date(milliseconds))
    }

    fun defaultDateFormatter(date: Date): String {
        val formatter = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault())
        return formatter.format(date)
    }
}