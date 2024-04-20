package com.exam

import com.example.calculator.R

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    private var tvInput:TextView?=null
    var lastnumeric:Boolean=false
    var lastDot:Boolean=false
    var one_Decimal:Boolean=true
    var manyOperator:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitymain)
        tvInput=findViewById(R.id.tv_input)
    }
    fun onDigit(view:View){
        tvInput?.append((view as Button).text)
        lastnumeric=true
        lastDot=false
    }
    fun onCLR(view: View){
        tvInput?.text = ""
    }
    fun onDecimalPoint(view:View){
        if(lastnumeric && !lastDot && one_Decimal){
            tvInput?.append(".")
            lastDot=true
            lastnumeric=false
            one_Decimal=false
        }
    }

    fun onEqual(view:View){
        if(lastnumeric){
            var tvValue=tvInput?.text.toString()
            var prefix=""
            try {
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue=tvValue.substring(1)
                }
                if(tvValue.contains("-")) {
                    var splitValue=tvValue.split("-")
                    var one=splitValue[0]
                    var two =splitValue[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }

                    tvInput?.text=removeZeroAfterDot((one.toDouble()-two.toDouble()).toString())
                }else if(tvValue.contains("+")) {
                    var splitValue=tvValue.split("+")
                    var one=splitValue[0]
                    var two =splitValue[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }

                    tvInput?.text=removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }else if(tvValue.contains("*")) {
                    var splitValue=tvValue.split("*")
                    var one=splitValue[0]
                    var two =splitValue[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }

                    tvInput?.text=removeZeroAfterDot((one.toDouble()*two.toDouble()).toString())
                }else if(tvValue.contains("/")) {
                    var splitValue=tvValue.split("/")
                    var one=splitValue[0]
                    var two =splitValue[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }

                    tvInput?.text=removeZeroAfterDot((one.toDouble()/two.toDouble()).toString())
                }else if(tvValue.contains("%")) {
                    var splitValue=tvValue.split("%")
                    var one=splitValue[0]
                    var two =splitValue[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }

                    tvInput?.text=removeZeroAfterDot((one.toDouble()%two.toDouble()).toString())
                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeZeroAfterDot(result:String):String{
        var value=result
        if(result.contains(".0"))
            value=result.substring(0,result.length-2)

        return value
    }
    fun onOperator(view:View){
        tvInput?.text?.let {
            if(isOperatorAdded(it.toString())){
                manyOperator=true
            }
            if(lastnumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastnumeric=false
                lastDot=false
                one_Decimal=true
            }
            else if(lastnumeric && manyOperator){
                onEqual(view)
                tvInput?.append((view as Button).text)
                lastnumeric=false
                lastDot=false
                one_Decimal=true
            }
        }
    }
    private  fun isOperatorAdded(value:String):Boolean{
        if(value.startsWith("-")){
            return true
        }else if(value.contains("/")||
            value.contains("*")||
            value.contains("+")||
            value.contains("-")){
            return true
        }
        return false
    }
    fun backbutton(view: View){
        var s=tvInput?.text.toString()
        var n=s.length
        tvInput?.text=s.substring(0,n-1)

        val a=s[n-1]
        if(isOperator(a)){
            lastnumeric=true
        }
    }
    fun isOperator(last: Char):Boolean{
        return last=='/' || last=='+' || last=='-' || last=='*'
    }

}
