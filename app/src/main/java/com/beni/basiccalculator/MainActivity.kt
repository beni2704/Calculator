package com.beni.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    var lastNum: Boolean = false
    var lastDot: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)

    }

    fun onDigit(view :View){
        tvInput?.append((view as Button).text)
        lastNum = true
        lastDot = false
    }

    fun onClear(view :View){
        tvInput?.text=""
    }

    fun onDecimal(view :View){
        if(lastNum && !lastDot){
            tvInput?.append(".")
            lastNum = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let{
            if(lastNum && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNum = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View){
        if(lastNum){
            var tvVal = tvInput?.text.toString()
            var prefix = ""
            try{

                if(tvVal.startsWith("-")) {
                    prefix = "-"
                    tvVal = tvVal.substring(1)
                }
                if(tvVal.contains("-")){
                    val splitValue = tvVal.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = isZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }else  if(tvVal.contains("+")){
                    val splitValue = tvVal.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = isZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }else if(tvVal.contains("*")){
                    val splitValue = tvVal.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = isZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if(tvVal.contains("/")){
                    val splitValue = tvVal.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = isZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }

            }catch (e: java.lang.ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun isZeroAfterDot(result: String) : String{
        var res = result
        if(result.contains(".0")){
            res = result.substring(0, res.length - 2)
        }
        return res
    }

    private fun isOperatorAdded(value : String) : Boolean {
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }
}