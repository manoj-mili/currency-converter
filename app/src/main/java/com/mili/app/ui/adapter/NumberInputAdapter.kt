package com.mili.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mili.app.data.local.CurrencyConvertedValue
import com.mili.app.databinding.ItemNumberBinding
import java.util.*
import kotlin.collections.ArrayList

class NumberInputAdapter() :
    RecyclerView.Adapter<NumberInputAdapter.ViewHolder>() {
    private lateinit var currencyList: List<CurrencyConvertedValue>

    class ViewHolder(private val binding: ItemNumberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currencyValue: CurrencyConvertedValue) {
            binding.currencyValue = currencyValue
            binding.executePendingBindings()
        }
    }

    fun setUpData(currencyList: List<CurrencyConvertedValue>) {
        this.currencyList = currencyList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currencyList.get(position))
    }

    override fun getItemCount(): Int {
        if (::currencyList.isInitialized) {
            return currencyList.size
        }
        return 0
    }
}