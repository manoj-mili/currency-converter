package com.mili.app.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mili.app.R
import com.mili.app.common.InternetStatusUtil
import com.mili.app.data.local.entity.CurrencyValue
import com.mili.app.databinding.ActivityMainBinding
import com.mili.app.ui.adapter.NumberInputAdapter
import com.mili.app.ui.base.BaseActivity


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var adapter: NumberInputAdapter
    lateinit var fromArrayAdapter: ArrayAdapter<CurrencyValue>
    lateinit var connectionStatus: InternetStatusUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        injector.inject(this)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.adapter = adapter
        binding.noMatchMessage = false
        setupListeners()
        observeDataChanges()
    }

    private fun setupListeners() {
        binding.etAmount.addTextChangedListener {
            if (it != null) {
                viewModel.updateInput(it.toString())
                binding.etAmount.setSelection(it.length)
            }
        }

        binding.etSearchCurrency.doAfterTextChanged {
            if(it !=null)
            viewModel.updateSearchInput(it.toString())
        }
    }

    private fun observeDataChanges() {

        connectionStatus.observe(this, { connectionActive ->
            if (connectionActive)
                viewModel.callApiToFetchLiveCurrencyRates()
        })

        viewModel.getRateSelectionOptions().observe(this, Observer {
            fromArrayAdapter.addAll(it)
            binding.spFromCurrency.adapter = fromArrayAdapter
        })


        viewModel.getLocalExistingData().observe(this, {
            if (it != null) {
                adapter.setUpData(it)
                binding.noMatchMessage = adapter.itemCount == 0
            }
        })

        viewModel.getAPILastUpdatedTime().observe(this, Observer {
            binding.apiLastUpdated = it
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_action_info -> {
                showInfoDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showInfoDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.message_app_info)
        builder.apply {
            setPositiveButton(R.string.lbl_ok) { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.create().show()
    }
}