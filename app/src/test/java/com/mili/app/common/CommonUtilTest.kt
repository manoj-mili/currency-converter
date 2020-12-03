package com.mili.app.common

import com.mili.app.common.CommonUtil.formatDataForInsert
import com.mili.app.data.local.entity.CurrencyValue
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class CommonUtilTest {
    lateinit var quotes: Map<String, Double>

    @Before
    fun setup() {
        quotes = mapOf("USDAED" to 3.672977, "USDAFN" to 77.049923, "USDAMD" to 506.402891,
                "USDANG" to 1.795336, "USDAOA" to 654.817005, "USDAWG" to 1.8)
    }

    @Test
    fun formatDataForInsert_whenMapOfStringAndDoubleIsPassed_returnListOfCurrencyValue() {
        val expected = listOf(CurrencyValue("AED", 3.672977),
                CurrencyValue("AFN", 77.049923), CurrencyValue("AMD", 506.402891),
                CurrencyValue("ANG", 1.795336), CurrencyValue("AOA", 654.817005), CurrencyValue("AWG", 1.8))
        val result = formatDataForInsert(quotes)

        assertEquals(expected, result)
    }
}