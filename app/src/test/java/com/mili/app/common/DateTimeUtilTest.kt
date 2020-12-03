package com.mili.app.common

import com.mili.app.common.DateTimeUtil.getEnglishPrintableDateFromMillis
import com.mili.app.common.DateTimeUtil.isTimeElapsedFromLastAPICall
import com.mili.app.common.GlobalConstants.RATES_UPDATED__DATE_FORMAT
import org.junit.Assert
import org.junit.Test

class DateTimeUtilTest {

    @Test
    fun isTimeElapsedFromLastAPICall_whenTimeDiffLessThanThirtyMins_returnFalse() {
        val timeToTest = System.currentTimeMillis() - 300000 // 5 min
        val result = isTimeElapsedFromLastAPICall(timeToTest)
        Assert.assertEquals(false, result)
    }

    @Test
    fun isTimeElapsedFromLastAPICall_whenTimeDiffGreaterThanThirtyMins_returnTrue() {
        val timeToTest = System.currentTimeMillis() - 2400000 // 40 min
        val result = isTimeElapsedFromLastAPICall(timeToTest)
        Assert.assertEquals(true, result)
    }

    @Test
    fun getEnglishPrintableDateFromMillis_whenValidTimeStampIsPassed_returnCorrectFormattedTime() {
        val timeToTest = 1606763009142
        val result = getEnglishPrintableDateFromMillis(timeToTest, RATES_UPDATED__DATE_FORMAT)
        Assert.assertEquals("01-12-2020 12:33:29", result)
    }


    @Test
    fun getEnglishPrintableDateFromMillis_whenZeroIsPassed_returnNA() {
        val timeToTest = 0L
        val result = getEnglishPrintableDateFromMillis(timeToTest, RATES_UPDATED__DATE_FORMAT)
        Assert.assertEquals("NA", result)
    }
}