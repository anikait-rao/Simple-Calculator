package com.example.simplecalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {

    // TextView used to display the input/output
    lateinit var input: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = findViewById(R.id.input)
    }

    // Represent whether the lastly pressed key is numeric
    var lastValueIsNumeric: Boolean = false

    // Represent that current state is in error or not
    var error: Boolean = false

    // If true, do not allow to add another DOT
    var dotUsed: Boolean = false


    // If a number button is pressed, add it to the text view.
    fun numberPressed(view: View)
    {
        val num = (view as Button).text
        if (error == true)
        {
            input.text = num
            error = false
        }
        else
        {
            input.append(num)
        }
        lastValueIsNumeric = true
    }


    // If a decimal point is clicked legally, add it to the text view.
    fun decimalPressed(view : View)
    {
        if (error == false && dotUsed == false && lastValueIsNumeric == true)
        {
            input.append(".")
            lastValueIsNumeric = false
            dotUsed = true
        }
    }

    // If an operator is clicked legally, add it to the text view.
    fun operatorPressed(view : View)
    {
        val operator = (view as Button).text
        if (error == false && input.length() == 0 && operator == "-")
        {
            input.append(operator)
        }
        else if (lastValueIsNumeric == true && error == false)
        {
            input.append(operator)
            lastValueIsNumeric = false
            dotUsed = false
        }
    }


    // If clear is clicked, wipe the textview and reset the variables.
    fun clearPressed(view : View)
    {
        this.input.text = ""
        lastValueIsNumeric = false
        error = false
        dotUsed = false
    }

    // Calculate the output of the expression in textview
    fun equalsPressed(view : View)
    {
        if (lastValueIsNumeric == true && error == false)
        {
            val expressionAsString = input.text.toString()

            // Use exp4j library to build an expression
            val expression = ExpressionBuilder(expressionAsString).build()

            // Evaluate expression if valid otherwise produce error
            try
            {
                val ans = expression.evaluate()
                input.text = ans.toString()
                dotUsed = "." in input.text
            }
            catch (ex: ArithmeticException)
            {
                input.text = "Error"
                lastValueIsNumeric = false
                error = true
            }
        }
    }
}
