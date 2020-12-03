package com.mili.app.simpledi

import com.mili.app.data.local.repo.CurrencyRepo

class ViewModelModule(private val activityModule: ActivityModule) {
    private val currencyRepo: CurrencyRepo get() = activityModule.repo
    val mainViewModelFactory = CustomViewModelProvider(currencyRepo)
    val splashViewModelFactory = CustomViewModelProvider(currencyRepo,activityModule.isFirstLoadDone)
}