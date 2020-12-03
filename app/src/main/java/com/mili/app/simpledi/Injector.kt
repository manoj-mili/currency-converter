package com.mili.app.simpledi

import android.R
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.mili.app.common.InternetStatusUtil
import com.mili.app.data.local.entity.CurrencyValue
import com.mili.app.ui.adapter.NumberInputAdapter
import com.mili.app.ui.main.MainActivity
import com.mili.app.ui.main.MainViewModel
import com.mili.app.ui.splash.SplashActivity
import com.mili.app.ui.splash.SplashViewModel

class Injector(private val viewModelModule: ViewModelModule) {

    fun inject(mainActivity: MainActivity) {
        mainActivity.viewModel = ViewModelProvider(mainActivity,
                viewModelModule.mainViewModelFactory).get(MainViewModel::class.java)
        mainActivity.adapter = NumberInputAdapter()
        mainActivity.fromArrayAdapter =
                ArrayAdapter<CurrencyValue>(mainActivity, R.layout.simple_list_item_activated_1)
        mainActivity.connectionStatus = InternetStatusUtil(mainActivity)
    }

    fun inject(splashActivity: SplashActivity) {
        splashActivity.connectionStatus = InternetStatusUtil(splashActivity)
        splashActivity.viewModel = ViewModelProvider(splashActivity,
                viewModelModule.splashViewModelFactory).get(SplashViewModel::class.java)
    }


}