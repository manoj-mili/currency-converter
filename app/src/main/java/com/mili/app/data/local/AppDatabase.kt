package com.mili.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mili.app.data.local.dao.CurrencyValueDao
import com.mili.app.data.local.entity.CurrencyValue

@Database(entities = [CurrencyValue::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyValueDao(): CurrencyValueDao
    companion object {
        fun defaultDB(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "currency-value-db")
                .build()
    }
}