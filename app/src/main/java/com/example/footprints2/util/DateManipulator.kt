package com.example.footprints2.util

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object DateManipulator {
    private const val FORMAT_PATTERN_DATE = "yyyy/MM/dd"
    private const val FORMAT_PATTERN_TIME = "HH:mm:ss"
    private const val FORMAT_PATTERN_DATE_AND_TIME = "$FORMAT_PATTERN_DATE $FORMAT_PATTERN_TIME"
    private const val FORMAT_PATTERN_DATE2 = "yyyy年MM月dd日"
    private const val FORMAT_PATTERN_0 = "00:00:00"

    fun convertTimeStampToDate(timestamp: Long): String {
        val formatter = SimpleDateFormat(FORMAT_PATTERN_DATE, Locale.getDefault())
        return formatter.format(timestamp)
    }

    fun convertDateToTimestamp(date: String): Long {
        val formatter = SimpleDateFormat(FORMAT_PATTERN_DATE, Locale.getDefault())
        return formatter.parse(date)?.time ?: 0
    }

    fun convertTimeStampToTime(timestamp: Long): String {
        val formatter = SimpleDateFormat(FORMAT_PATTERN_TIME, Locale.getDefault())
        return formatter.format(timestamp)
    }

    fun convertTimeToTimestamp(time: String): Long {
        val formatter = SimpleDateFormat(FORMAT_PATTERN_TIME, Locale.getDefault())
        return formatter.parse(time)?.time ?: 0
    }
}