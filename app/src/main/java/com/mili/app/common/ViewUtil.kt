package com.mili.app.common

import android.view.View
import com.google.android.material.snackbar.Snackbar

object ViewUtil {

    fun showSnackBar(layout: View, message: String, indefinite: Boolean) {
        if (indefinite) {
            val snackBar = Snackbar.make(layout, message, Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction("OK") { v: View? -> snackBar.dismiss() }
            snackBar.show()
        } else {
            Snackbar.make(layout, message, Snackbar.LENGTH_LONG).show()
        }
    }
}