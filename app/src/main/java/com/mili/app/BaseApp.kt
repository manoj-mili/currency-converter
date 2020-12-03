package com.mili.app

import android.app.Application
import android.util.Log
import androidx.work.*
import com.mili.app.data.local.repo.CurrencyRepo
import com.mili.app.service.SyncLatestRatesWorker
import com.mili.app.simpledi.AppModule
import com.mili.app.simpledi.CustomWorkManagerProvider
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.concurrent.TimeUnit

class BaseApp : Application(), Configuration.Provider {
    lateinit var appModule: AppModule
    override fun onCreate() {
        super.onCreate()
        appModule = AppModule(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        //Once First Load is done then set up the sync task it will be executed in next app opening
        // given data is synced in first
        if (appModule.isFirstLoadDone) {
            syncUpdatedRates()
        }
    }


    private fun syncUpdatedRates() {
        val workManager = WorkManager.getInstance(this)
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) //Only run if internet connection is available
                .build()
        val periodicWorkRequest =
                PeriodicWorkRequest.Builder(SyncLatestRatesWorker::class.java, 30, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .addTag("sync-rate-updates")
                        .build()
        try {
            workManager.enqueueUniquePeriodicWork(
                    "sync-rate-updates",
                    ExistingPeriodicWorkPolicy.KEEP,
                    periodicWorkRequest
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val currencyRepo: CurrencyRepo = appModule.currencyRepo
        val builder = Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .setWorkerFactory(CustomWorkManagerProvider(currencyRepo))
        return builder.build()
    }

}