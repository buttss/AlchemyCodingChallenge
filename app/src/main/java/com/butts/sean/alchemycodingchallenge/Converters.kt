package com.butts.sean.alchemycodingchallenge

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toKidsList(value: String): List<Long> {
        val kids = value.split(", ").flatMap { s -> listOf(s.toLong()) }
        return kids
    }

    @TypeConverter
    fun toString(value: List<Long>): String {
        return value.joinToString { it.toString() }
    }
}