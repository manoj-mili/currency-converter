package com.mili.app.common

import android.content.Context
import android.content.Intent
import com.mili.app.data.local.entity.CurrencyValue

object CommonUtil {
    /**
     * helper function to format map to list of Currency
     */
    fun formatDataForInsert(quotes: Map<String, Double>): List<CurrencyValue> {
        val currencyValues: MutableList<CurrencyValue> = mutableListOf()
        for ((key, value) in quotes) {
            val updatedKey = key.replaceFirst(GlobalConstants.DEFAULT_SOURCE, "")
            currencyValues.add(CurrencyValue(updatedKey, value = value))
        }
        return currencyValues
    }


    fun startActivityAsNewTask(intent: Intent, context: Context) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        context.startActivity(intent)
    }
}