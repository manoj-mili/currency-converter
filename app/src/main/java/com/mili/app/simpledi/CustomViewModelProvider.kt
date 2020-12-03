package com.mili.app.simpledi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mili.app.data.local.repo.CurrencyRepo
import com.mili.app.ui.main.MainViewModel
import com.mili.app.ui.splash.SplashViewModel

class CustomViewModelProvider(private val repo: CurrencyRepo) :
        ViewModelProvider.NewInstanceFactory() {
    private var isFirstLoad: Boolean = false

    constructor(repo: CurrencyRepo, isFirstLoad: Boolean) : this(repo) {
        this.isFirstLoad = isFirstLoad
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repo) as T
        } else if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(repo, isFirstLoad) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class ${modelClass.canonicalName}")
    }
}