package com.mili.app.simpledi

import android.app.Application
import androidx.preference.PreferenceManager
import com.mili.app.BuildConfig
import com.mili.app.common.GlobalConstants
import com.mili.app.common.GlobalConstants.IS_FIRST_DONE
import com.mili.app.data.api.APIService
import com.mili.app.data.local.AppDatabase
import com.mili.app.data.local.repo.CurrencyRepo
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppModule(private val application: Application) {
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    private val retrofit: Retrofit by lazy {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        //Only enable logging when in debug mode
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(logging)
        }
        Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
    }

    private val apiService: APIService by lazy {
        retrofit.create(APIService::class.java)
    }

    private val database: AppDatabase by lazy {
        AppDatabase.defaultDB(application)
    }

    val isFirstLoadDone get() = sharedPref.getBoolean(IS_FIRST_DONE, false)

    val currencyRepo: CurrencyRepo by lazy {
        CurrencyRepo(
                apiService, database.currencyValueDao(),
                sharedPref
        )
    }

}