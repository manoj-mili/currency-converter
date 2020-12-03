package com.mili.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mili.app.R
import com.mili.app.common.CommonUtil.startActivityAsNewTask
import com.mili.app.common.InternetStatusUtil
import com.mili.app.common.ViewUtil
import com.mili.app.data.api.util.APIResult
import com.mili.app.databinding.ActivitySplashBinding
import com.mili.app.ui.base.BaseActivity
import com.mili.app.ui.main.MainActivity
import timber.log.Timber

class SplashActivity : BaseActivity() {

    lateinit var connectionStatus: InternetStatusUtil
    lateinit var viewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
        injector.inject(this)
        binding.loading = false
        observeDataChanges()
    }

    private fun observeDataChanges() {

        connectionStatus.observe(this, Observer { connectionActive ->
            binding.connectionActive = connectionActive
            if (connectionActive)
                viewModel.callApiToFetchLiveCurrencyRates()
        })

        viewModel.getNetworkCallStatus().observe(this, Observer {
            when (it) {
                is APIResult.Success -> {
                    binding.loading = false
                    startMainActivity()
                }
                is APIResult.Loading -> {
                    binding.loading = true
                }
                is APIResult.Failed -> {
                    binding.loading = false
                    ViewUtil.showSnackBar(
                            binding.root,
                            it.message ?: this.getString(R.string.message_connection_error),
                            false
                    )
                }
            }
        })

        viewModel.isFirstLoadDone().observe(this, {
            binding.isFirstLoadComplete = it
            if (it) {
                startMainActivity()
            }
        })
    }


    private fun startMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivityAsNewTask(intent, this)
        }, 1000)
    }
}