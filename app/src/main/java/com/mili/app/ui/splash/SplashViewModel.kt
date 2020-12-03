package com.mili.app.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mili.app.data.api.util.APIResult
import com.mili.app.data.local.repo.CurrencyRepo
import kotlinx.coroutines.launch

class SplashViewModel(private val repo: CurrencyRepo, private val isFirstLoadDone: Boolean) :
    ViewModel() {

    fun callApiToFetchLiveCurrencyRates() {
        viewModelScope.launch {
            if (!isFirstLoadDone) {
                repo.getLatestConversionRates()
            }
        }
    }


    fun getNetworkCallStatus(): LiveData<APIResult<String>> {
        return repo.getStatus()
    }

    fun isFirstLoadDone(): LiveData<Boolean> {
        return MutableLiveData(isFirstLoadDone)
    }
}