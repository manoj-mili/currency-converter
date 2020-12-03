package com.mili.app.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mili.app.data.local.CurrencyConvertedValue
import com.mili.app.data.local.entity.CurrencyValue

@Dao
interface CurrencyValueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(values: List<CurrencyValue>)

    @Query("SELECT * FROM CurrencyValue")
    fun getExistingConversionOptions(): LiveData<List<CurrencyValue>>

    @Query("SELECT value FROM CurrencyValue WHERE rateFor=:currency")
    fun getValueFor(currency: String): LiveData<Double>

    @Query("SELECT rateFor, printf('%.2f', ((1/(:fromCurrencyRate/value))*:input)) as updatedValue FROM CurrencyValue WHERE rateFor<>:selectedCurrency")
    fun getConvertedCurrencyValueForEmptySearch(input: Double, selectedCurrency: String, fromCurrencyRate: Double): LiveData<List<CurrencyConvertedValue>>

    @Query("SELECT rateFor, ((1/(:fromCurrencyRate/value))*:inputAmount) as updatedValue FROM CurrencyValue WHERE rateFor<>:fromCurrency AND rateFor LIKE :search")
    fun getConvertedCurrencyValueFor(inputAmount: Double, fromCurrency: String, fromCurrencyRate: Double, search: String): LiveData<List<CurrencyConvertedValue>>
}