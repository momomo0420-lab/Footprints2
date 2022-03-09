package com.example.footprints2.util

import java.text.SimpleDateFormat
import java.util.*

object DateManipulator {
    private const val FORMAT_PATTERN_DATE = "yyyy/MM/dd"
    private const val FORMAT_PATTERN_DATE_AND_TIME = "$FORMAT_PATTERN_DATE HH:mm:ss"
    private const val FORMAT_PATTERN_0 = "00:00:00"

    fun convertDateAndTimeToString(dateAndTime: Long): String {
        val formatter = SimpleDateFormat(FORMAT_PATTERN_DATE_AND_TIME, Locale.getDefault())
        return formatter.format(Date(dateAndTime))
    }

    fun convertDateAndTimeToDate(dateAndTime: Long): Long {
        val formatter = SimpleDateFormat(FORMAT_PATTERN_DATE, Locale.getDefault())
        var date = formatter.format(Date(dateAndTime))
        date += " $FORMAT_PATTERN_0"

        return formatter.parse(date)?.time ?: 0
    }

    fun nextDayOf(date: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.time = Date(date)
        calendar.add(Calendar.DAY_OF_MONTH, 1)

        return calendar.time.time
    }
}