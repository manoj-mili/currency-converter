package com.mili.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyValue(
    @PrimaryKey
    val rateFor: String,
    val value: Double
) {
    override fun toString(): String {
        return rateFor
    }
}

