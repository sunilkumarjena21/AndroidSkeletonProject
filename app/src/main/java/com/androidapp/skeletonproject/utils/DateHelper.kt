package com.androidapp.skeletonproject.utils

import org.joda.time.format.DateTimeFormat
import timber.log.Timber

fun String.toDateWithTime(): String {

    //If date is an empty string, we return it
    if (this.isEmpty()) return ""

    var stringShortDate: String
    val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

    try {
        println(this)

        val date = formatter.parseDateTime(this)

        var retMinutes = date.minuteOfHour.toString()
        if (retMinutes.length == 1) {
            retMinutes = "0" + retMinutes
        }

        stringShortDate =
            date.year.toString() + "-" + date.monthOfYear.toString() + "-" + date.dayOfMonth.toString() + " " + date.hourOfDay.toString() + ":" + retMinutes

        println(stringShortDate)

    } catch (e: org.joda.time.IllegalFieldValueException) {
        return ""
    }

    return stringShortDate
}

/**
 * Convert a lenght in minutes into HHMM format
 */
fun String.toHHMMString(): String {

    return try {
        val totalMinutes = this.fromMillisToMinutes().toInt()
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60

        var retHours = hours.toString()
        if (retHours.length == 1) {
            retHours = "0" + retHours
        }

        var retMinutes = minutes.toString()
        if (retMinutes.length == 1) {
            retMinutes = "0" + retMinutes
        }
        (retHours + "H" + retMinutes)
    } catch (e: NumberFormatException) {
        Timber.e(e)
        ""
    }
}

/**
 * Convert a date into a date without time
 */
fun String.toDateWithoutTime(): String {

    //If date is an empty string, we return it
    if (this.isEmpty()) return ""

    var stringShortDate: String
    val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

    try {
        println(this)

        val date = formatter.parseDateTime(this)
        stringShortDate =
            date.year.toString() + "-" + date.monthOfYear.toString() + "-" + date.dayOfMonth.toString()

        println(stringShortDate)

    } catch (e: org.joda.time.IllegalFieldValueException) {
        Timber.e(e)
        return ""
    }

    return stringShortDate
}

/**
 * Convert a date into a time without the date
 */
fun String.toTimeWithoutDate(): String {
    //If date is an empty string, we return it
    if (this.isEmpty()) return ""

    var stringShortDate: String
    val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

    try {
        println(this)

        val date = formatter.parseDateTime(this)

        var retMinutes = date.minuteOfHour.toString()
        if (retMinutes.length == 1) {
            retMinutes = "0" + retMinutes
        }
        stringShortDate =
            date.hourOfDay.toString() + ":" + retMinutes

        println(stringShortDate)

    } catch (e: org.joda.time.IllegalFieldValueException) {
        Timber.e(e)
        return ""
    }

    return stringShortDate
}

/**
 * Convert milliseconds to minutes
 */
fun String.fromMillisToMinutes(): String {

    return try {
        val totalMillis = this.toInt()
        val minutes = totalMillis / 60000
        var ret = minutes.toString()
        if (ret.length == 1) {
            ret = "0" + ret
        }
        ret
    } catch (e: NumberFormatException) {
        Timber.e(e)
        ""
    }
}

/**
 * Convert from minutes to millis
 */
fun String.fromMinutesToMillis(): String {
    return try {
        val totalMinutes = this.toInt()
        val millis = totalMinutes * 60000
        millis.toString()
    } catch (e: NumberFormatException) {
        Timber.e(e)
        ""
    }
}
