package com.gstormdev.diceroller.db.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

private val moshi = Moshi.Builder().build()

class ListConverter {

    @TypeConverter
    fun fromString(json: String): List<Int> {
        val listType = Types.newParameterizedType(List::class.java, Int::class.java)
        val adapter = moshi.adapter<List<Int>>(listType)
        return adapter.fromJson(json) ?: emptyList()
    }

    @TypeConverter
    fun toString(list: List<Int>): String {
        val listType = Types.newParameterizedType(List::class.java, Int::class.java)
        val adapter = moshi.adapter<List<Int>>(listType)
        return adapter.toJson(list)
    }
}