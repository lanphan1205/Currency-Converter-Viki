package com.example.rakutenviki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var baseCurrency = "EUR" // init value
    private var convertedToCurrency = "USD" // init value
    private var conversionRate = 0f;

    private lateinit var ETfirstConversion : EditText
    private lateinit var ETsecondConversion: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ETfirstConversion = findViewById(R.id.et_firstConversion);
        ETsecondConversion = findViewById(R.id.et_secondConversion);

        spinnerSetup();
        textChanged();
    }

    private fun textChanged() {
        ETfirstConversion.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                getApiResult()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

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
                baseCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
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
                convertedToCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
            }
        })
    }

    private fun getApiResult() {
        if (ETfirstConversion.text.isNotEmpty() && ETfirstConversion.text.isNotBlank()) {
            val API = resources.getString(R.string.BASE_URL)  + baseCurrency;
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val apiResult = URL(API).readText();
                    val jsonObject = JSONObject(apiResult);
                    conversionRate = jsonObject.getJSONObject("conversion_rates").getDouble("$convertedToCurrency").toFloat()
                    Log.d("Main", "$conversionRate")
                    Log.d("Main", apiResult)
                    // update main UI thread
                    withContext(Dispatchers.Main) {
                        // Calculate convertedToCurrency
                        val text = (ETfirstConversion.text.toString().toFloat() * conversionRate).toString()
                        ETsecondConversion.setText(text)
                    }
                } catch (e: Exception) {
                    Log.e("Main", "$e")
                }
            }
        }
    }
}