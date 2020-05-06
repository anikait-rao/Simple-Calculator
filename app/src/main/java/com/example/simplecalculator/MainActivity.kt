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

    // Represent whether the lastly pressed key is numeric
    var lastValueIsNumeric: Boolean = false

    // Represent that current state is in error or not
    var error: Boolean = false

    // If true, do not allow to add another DOT
    var dotUsed: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = findViewById(R.id.input)
    }

    // If a number button is pressed, add it to the text view.
    fun numberPressed(view: View)
    {
        if (error == true)
        {
            input.text = (view as Button).text
            error = false
        }
        else
        {
            input.append((view as Button).text)
        }
        lastValueIsNumeric = true
    }


    // If a decimal point is clicked legally, add it to the text view.
    fun decimalPressed(view: View)
    {
        if (error == false && dotUsed == false && lastValueIsNumeric == true)
        {
            input.append(".")
            lastValueIsNumeric = false
            dotUsed = true
        }
    }

    // If an operator is clicked, add it to the text view.
    fun operatorPressed(view: View)
    {
        if (lastValueIsNumeric == true && error == false)
        {
            input.append((view as Button).text)
            lastValueIsNumeric = false
            dotUsed = false
        }
    }

    // If clear is clicked, wipe the textview and reset the variables.
    fun clearPressed(view: View)
    {
        this.input.text = ""
        lastValueIsNumeric = false
        error = false
        dotUsed = false
    }

    // Calculate the output of the expression in textview
    fun equalsPressed(view: View)
    {
        if (lastValueIsNumeric == true && error == false)
        {
            val expressionAsString = input.text.toString()

            // Use exp4j library to build an expression
            val expression = ExpressionBuilder(expressionAsString).build()

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