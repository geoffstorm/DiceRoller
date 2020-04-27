package com.gstormdev.diceroller.util

import androidx.databinding.InverseMethod
import com.google.android.material.textfield.TextInputEditText

object Converter {
    @InverseMethod("stringToInt")
    @JvmStatic
    fun intToString(view: TextInputEditText, value: Int?): String {
        val text = view.text.toString()
        val parsed = text.toIntOrNull()
        if (parsed == value) return text
        return value.toString()
    }

    @JvmStatic
    fun stringToInt(view: TextInputEditText, value: String): Int? {
        return value.toIntOrNull()
    }
}