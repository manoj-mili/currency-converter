package com.mili.app.simpledi

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.mili.app.data.local.repo.CurrencyRepo
import com.mili.app.service.SyncLatestRatesWorker

class CustomWorkManagerProvider(private val currencyRepo: CurrencyRepo) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return SyncLatestRatesWorker(currencyRepo, appContext, workerParameters)
    }
}