package com.mili.app.ui

import android.widget.TextView
import android.widget.TimePicker
import androidx.databinding.BindingAdapter
import com.mili.app.R
import com.mili.app.common.DateTimeUtil.getEnglishPrintableDateFromMillis
import com.mili.app.common.GlobalConstants.RATES_UPDATED__DATE_FORMAT
import timber.log.Timber
import java.lang.NumberFormatException
import java.text.NumberFormat
import java.util.*


@BindingAdapter("formattedTime")
fun formattedTime(view: TextView, time: Long) {
    val formattedDate: String =
        getEnglishPrintableDateFromMillis(time, RATES_UPDATED__DATE_FORMAT)
    val formattedText = view.context.getString(R.string.lbl_last_sync_time, formattedDate)
    view.text = formattedText
}

@BindingAdapter("formatConversionAmount")
fun formatConversionAmount(view: TextView, amount: Double) {
    val formattedAmount =
        NumberFormat.getNumberInstance(Locale.US).format(String.format("%.2f", amount).toDouble())
    view.text = formattedAmount
}
