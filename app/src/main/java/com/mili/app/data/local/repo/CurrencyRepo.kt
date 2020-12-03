package com.mili.app.data.local.repo

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mili.app.BuildConfig
import com.mili.app.common.CommonUtil.formatDataForInsert
import com.mili.app.common.DateTimeUtil.isTimeElapsedFromLastAPICall
import com.mili.app.common.GlobalConstants.ACCESS_KEY
import com.mili.app.common.GlobalConstants.API_RATES_SOURCE_KEY
import com.mili.app.common.GlobalConstants.API_RATES_UPDATE_TIME_KEY
import com.mili.app.common.GlobalConstants.IS_FIRST_DONE
import com.mili.app.common.GlobalConstants.LAST_SYNC_TIME_KEY
import com.mili.app.data.api.APIService
import com.mili.app.data.api.util.APIResult
import com.mili.app.data.local.CurrencyConvertedValue
import com.mili.app.data.local.CurrencySelection
import com.mili.app.data.local.dao.CurrencyValueDao
import com.mili.app.data.local.entity.CurrencyValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.UnknownHostException

class CurrencyRepo(
        private val apiService: APIService,
        private val dao: CurrencyValueDao,
        private val sharedPref: SharedPreferences
) {
    private val apiResponseStatus = MutableLiveData<APIResult<String>>(APIResult.Init())
    private val ratesLastUpdatedByAPI =
            MutableLiveData(sharedPref.getLong(LAST_SYNC_TIME_KEY, 0L))

    suspend fun getLatestConversionRates() {
        val lastSyncTime: Long = sharedPref.getLong(LAST_SYNC_TIME_KEY, 0L)

        if (isTimeElapsedFromLastAPICall(lastSyncTime)) {
            apiResponseStatus.value = APIResult.Loading()
            val params = mapOf(ACCESS_KEY to BuildConfig.APIKEY)
            try {
                val response = apiService.getConversionFor(params)
                if (response.success) {
                    val currencyValueList: List<CurrencyValue> = formatDataForInsert(response.quotes)
                    apiResponseStatus.postValue(APIResult.Success())
                    withContext(Dispatchers.IO) {
                        dao.insert(currencyValueList)
                        sharedPref.edit().putLong(LAST_SYNC_TIME_KEY, System.currentTimeMillis())
                                .apply()
                        sharedPref.edit().putLong(API_RATES_UPDATE_TIME_KEY, response.timestamp).apply()
                        sharedPref.edit().putString(API_RATES_SOURCE_KEY, response.source).apply()
                        sharedPref.edit().putBoolean(IS_FIRST_DONE, true).apply()
                        ratesLastUpdatedByAPI.postValue(System.currentTimeMillis())
                    }
                } else {
                    apiResponseStatus.value = APIResult.Failed()
                }
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> {
                        if (throwable is UnknownHostException) {
                            apiResponseStatus.postValue(APIResult.Failed(throwable, "There Is Some Issue With The Network Pleas Try Again Later"))
                        } else {
                            apiResponseStatus.postValue(APIResult.Failed(throwable))
                        }
                    }
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = throwable.response()?.errorBody().toString()
                        apiResponseStatus.postValue(APIResult.Failed(throwable, errorResponse))
                    }
                    else -> {
                        apiResponseStatus.postValue(APIResult.Failed())
                    }
                }
            }
        }
    }

    fun getStatus(): LiveData<APIResult<String>> {
        return apiResponseStatus
    }

    fun getRatesOptions(): LiveData<List<CurrencyValue>> {
        return dao.getExistingConversionOptions()
    }


    fun getAPILastUpdatedTime(): LiveData<Long> {
        return ratesLastUpdatedByAPI
    }

    fun getExistingCurrencyValues(selection: CurrencySelection): LiveData<List<CurrencyConvertedValue>> {
        if (selection.search.isEmpty()) {
            return dao.getConvertedCurrencyValueForEmptySearch(selection.inputAmount, selection.fromCurrency, selection.fromCurrencyRate)
        } else {
            return dao.getConvertedCurrencyValueFor(selection.inputAmount, selection.fromCurrency, selection.fromCurrencyRate, "%${selection.search}%")
        }
    }
}