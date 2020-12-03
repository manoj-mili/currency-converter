package com.mili.app.common

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateTimeUtil {

    fun isTimeElapsedFromLastAPICall(lastSyncTime: Long): Boolean {
        val diffInMs = System.currentTimeMillis() - lastSyncTime
        return lastSyncTime == 0L || TimeUnit.MILLISECONDS.toMinutes(diffInMs) > 30
    }


    fun getEnglishPrintableDateFromMillis(time: Long, format: String): String {
        if (time == 0L) {
            return "NA"
        }
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        val date = Date(time)
        return sdf.format(date)
    }
}