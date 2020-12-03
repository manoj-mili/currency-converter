package com.mili.app.simpledi

import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.mili.app.common.GlobalConstants
import com.mili.app.data.local.repo.CurrencyRepo

class ActivityModule(private val activity: AppCompatActivity, private val appModule: AppModule) {
    val isFirstLoadDone get() = appModule.isFirstLoadDone
    val repo: CurrencyRepo get() = appModule.currencyRepo
}