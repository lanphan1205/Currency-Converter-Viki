package com.example.rakutenviki.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rakutenviki.model.CurrencyExchange
import com.example.rakutenviki.service.Repository

class MainActivityViewModel : ViewModel() {
    private val currencyExchange : MutableLiveData<CurrencyExchange> by lazy {
        Repository.getCurrencyExchangeData()
    }

    fun getCurrencyExchange(): LiveData<CurrencyExchange> {
        return currencyExchange
    }

    fun queryCurrencyExchange(baseCurrency: String, convertedToCurrency: String) {
        // query from CE Repository
        Repository.queryCurrencyExchange(baseCurrency, convertedToCurrency)
    }
}