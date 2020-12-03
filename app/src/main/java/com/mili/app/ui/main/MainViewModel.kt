package com.mili.app.ui.main

import android.widget.AdapterView
import androidx.lifecycle.*
import com.mili.app.data.local.CurrencyConvertedValue
import com.mili.app.data.local.CurrencySelection
import com.mili.app.data.local.entity.CurrencyValue
import com.mili.app.data.local.repo.CurrencyRepo
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

class MainViewModel(private val repo: CurrencyRepo) : ViewModel() {
    val inputValue = MutableLiveData("0")
    private lateinit var fromRateCurrency: CurrencyValue
    private var _value = ""
    private var inputSearch = ""
    private val currencySelection: MutableLiveData<CurrencySelection> = MutableLiveData()

    fun callApiToFetchLiveCurrencyRates() {
        viewModelScope.launch {
            repo.getLatestConversionRates()
        }
    }

    fun getLocalExistingData(): LiveData<List<CurrencyConvertedValue>> {
        return Transformations.switchMap(currencySelection) {
            repo.getExistingCurrencyValues(it)
        }
    }

    fun onFromOptionSelect(parent: AdapterView<*>, position: Int) {
        fromRateCurrency = parent.adapter.getItem(position) as CurrencyValue
        updateCurrencySelection()
    }

    fun getRateSelectionOptions(): LiveData<List<CurrencyValue>> {
        return repo.getRatesOptions()
    }

    fun getAPILastUpdatedTime(): LiveData<Long> {
        return repo.getAPILastUpdatedTime()
    }

    private fun sanitizeInput(value: String): String {
        return value.replace(",", "")
    }

    fun updateInput(input: String) {
        if (input.startsWith('.')) {
            _value = ""
            inputValue.value = ""
            return
        } else if (input.endsWith('.')) {
            return
        }
        _value = sanitizeInput(input)
        updateCurrencySelection()
    }

    private fun updateCurrencySelection() {
        if (_value.isNotEmpty() && ::fromRateCurrency.isInitialized) {
            currencySelection.value = CurrencySelection(fromRateCurrency.rateFor,
                    fromRateCurrency.value, _value.toDouble(), inputSearch)
            inputValue.value = NumberFormat.getNumberInstance(Locale.US).format(String.format("%.2f",
                _value.toDouble()).toDouble())
        } else if (_value.isEmpty() && ::fromRateCurrency.isInitialized) {
            currencySelection.value = CurrencySelection(fromRateCurrency.rateFor,
                    fromRateCurrency.value, 0.0, inputSearch)
        }
    }

    fun updateSearchInput(search: String) {
        inputSearch = search
        updateCurrencySelection()
    }
}