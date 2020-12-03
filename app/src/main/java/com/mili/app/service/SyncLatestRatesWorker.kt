package com.mili.app.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mili.app.data.local.repo.CurrencyRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * background task to sync messages and updates runs once every 60 min (there are known issues with worker
 * on phones with chinese rom as the end up running on battery saving mode
 */
class SyncLatestRatesWorker(private val currencyRepo: CurrencyRepo, context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            currencyRepo.getLatestConversionRates()
            return@withContext Result.success()
        }
    }
}