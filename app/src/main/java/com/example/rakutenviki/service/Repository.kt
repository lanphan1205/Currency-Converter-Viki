package com.example.rakutenviki.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.rakutenviki.model.CurrencyExchange
import kotlinx.coroutines.*
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class Repository {
    companion object {
        private const val baseUrl = "https://v6.exchangerate-api.com/v6/566117992c7c673408f6b53e/latest/"
        private val currencyExchange: MutableLiveData<CurrencyExchange> by lazy {
            MutableLiveData<CurrencyExchange>()
        }
        fun getCurrencyExchangeData(): MutableLiveData<CurrencyExchange> {
            return currencyExchange
        }
        fun queryCurrencyExchange(baseCurrency: String, convertedToCurrency: String) {
            val API = baseUrl + baseCurrency;
            CoroutineScope(Dispatchers.Default).launch {
                launch(Dispatchers.IO){
                    try {
                        val apiResult = URL(API).readText();
                        val jsonObject = JSONObject(apiResult);
                        val conversionRate = jsonObject.getJSONObject("conversion_rates").getDouble(convertedToCurrency).toFloat()
                        withContext(Dispatchers.Main) {
                            currencyExchange.value = CurrencyExchange(baseCurrency, convertedToCurrency, conversionRate)
                        }
                    } catch (e: Exception) {
                        Log.e("Main", "$e")
                    }
                }
            }
        }

    }
}