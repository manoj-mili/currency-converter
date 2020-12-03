package com.mili.app.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData

/**
 * Helper Class to check internet connection availability if any lifecycle owner is available
 * to listen to changes
 */
class InternetStatusUtil(private val context: Context) : LiveData<Boolean>() {

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(internetStatus, filter)
    }


    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(internetStatus)
    }


    private val internetStatus: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
                val noConnectivity =
                        intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
                postValue(!noConnectivity)
            }
        }
    }
}