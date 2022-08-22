package com.example.rakutenviki.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.rakutenviki.model.CurrencyExchange
import com.example.rakutenviki.viewmodel.MainActivityViewModel
import com.example.rakutenviki.R

class MainActivity : AppCompatActivity() {

    private lateinit var mMainActivityViewModel: MainActivityViewModel

    private lateinit var ETfirstConversion : EditText
    private lateinit var ETsecondConversion: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ETfirstConversion = findViewById(R.id.et_firstConversion);
        ETsecondConversion = findViewById(R.id.et_secondConversion);

        mMainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mMainActivityViewModel.queryCurrencyExchange("EUR", "USD") // avoid initial value of LiveData to be null and for initial query
        mMainActivityViewModel.getCurrencyExchange().observe(this,
            Observer<CurrencyExchange> { currencyExchange ->
                // check for initial state if empty
                if (ETfirstConversion.text.isNotEmpty()) {
                    val text = currencyExchange.calculate(ETfirstConversion.text.toString().toFloat()).toString()
                    ETsecondConversion.setText(text)
                }
        })

        spinnerSetup();
        textChanged();
    }

    private fun textChanged() {
        ETfirstConversion.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (ETfirstConversion.text.isNotEmpty()) {
                    val currencyExchange = mMainActivityViewModel.getCurrencyExchange().value
                    if (currencyExchange != null) {
                        mMainActivityViewModel.queryCurrencyExchange(currencyExchange.getBaseCurrency(),
                            currencyExchange.getConvertedToCurrency())
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        })
    }

    private fun spinnerSetup() {
        spinnerFromSetup()
        spinnerToSetup()
    }

    private fun spinnerFromSetup() {
        val spinner : Spinner = findViewById(R.id.spinner_firstConversion)
        ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // set spinner adapter
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = (object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val currencyExchange = mMainActivityViewModel.getCurrencyExchange().value
                if (currencyExchange != null) {
                    val baseCurrency = parent?.getItemAtPosition(position).toString()
                    mMainActivityViewModel.queryCurrencyExchange(baseCurrency, currencyExchange.getConvertedToCurrency())
                }
            }
        })
    }

    private fun spinnerToSetup() {
        val spinner : Spinner = findViewById(R.id.spinner_secondConversion)
        ArrayAdapter.createFromResource(
            this,
            R.array.currencies2,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // set spinner adapter
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = (object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) { }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val currencyExchange = mMainActivityViewModel.getCurrencyExchange().value
                if (currencyExchange != null) {
                    val convertedToCurrency = parent?.getItemAtPosition(position).toString()
                    mMainActivityViewModel.queryCurrencyExchange(currencyExchange.getBaseCurrency(), convertedToCurrency)
                }
            }
        })
    }

}