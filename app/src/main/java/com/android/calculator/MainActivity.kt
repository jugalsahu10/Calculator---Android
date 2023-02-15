package com.android.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var decimal: Boolean = false
    private var lastNumeric: Boolean = false

    private var tvInput: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        this.tvInput?.append((view as Button).text)
        this.lastNumeric = true
//        Toast.makeText(this, "Button clicked!", Toast.LENGTH_LONG).show()
    }

    fun onClear(view: View) {
        this.tvInput?.text = ""
        this.lastNumeric = false
        this.decimal = false
    }

    fun onDecimalPoint(view: View) {
        if (!this.decimal) {
            this.decimal = true
            this.lastNumeric = false
            this.tvInput?.append(".")
        }
    }

    fun onEqual(view: View) {
        tvInput?.text?.let {
            var value = it
            if (this.lastNumeric) {
                var prefix = ""
                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1)
                }
                val operators = arrayListOf("-", "+", "/", "*")
                for (operator in operators) {
                    if (value.contains(operator)) {
                        val splitValue = value.split(operator)
                        var firstValue = splitValue[0]
                        val secondValue = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            firstValue = "-" + firstValue
                        }
                        tvInput?.text = performOperation(
                            operator,
                            firstValue.toDouble(),
                            secondValue.toDouble()
                        ).toString()
                        this.lastNumeric = true
                        this.decimal = false
                    }
                }
            }
        }
    }

    private fun performOperation(
        operator: String,
        firstValue: Double,
        secondValue: Double
    ): Double {
        return when (operator) {
            "-" -> firstValue - secondValue
            "+" -> firstValue + secondValue
            "/" -> firstValue / secondValue
            "*" -> firstValue * secondValue
            else -> 0.0
        }
    }


    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (this.lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                this.lastNumeric = false
            }
        }
    }

    fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }
}


