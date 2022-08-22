package com.example.rakutenviki.model

class CurrencyExchange(
    private val baseCurrency: String,
    private val convertedToCurrency: String,
    private val conversionRate: Float
) {
    fun getBaseCurrency(): String {
        return baseCurrency
    }
    fun getConvertedToCurrency(): String {
        return convertedToCurrency
    }
    fun calculate(baseAmount: Float): Float {
        return baseAmount * conversionRate
    }
}