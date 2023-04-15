package com.example.bmicalculator

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var sf : SharedPreferences
    private lateinit var sfEditor: SharedPreferences.Editor

    private lateinit var height : EditText
    private lateinit var weight : EditText

    private lateinit var bmi : TextView
    private lateinit var statment : TextView

    private lateinit var calculateButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sf = getSharedPreferences("Shared Preferece", MODE_PRIVATE)
        sfEditor = sf.edit()
        height= findViewById(R.id.edittext_height)
        weight=findViewById(R.id.edittext_weight)

        bmi = findViewById(R.id.textview_bmi_result)
        statment = findViewById(R.id.textview_healthy)

        calculateButton = findViewById(R.id.button_calculate)
        calculateButton.setOnClickListener {
            if (height.text.toString() =="" ||weight.text.toString() ==""){
                Toast.makeText(this, "Enter the weight and height both", Toast.LENGTH_SHORT).show()
            }
            else{
                calculateBMI(weight.text.toString().toDouble() , height.text.toString().toDouble())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun calculateBMI(weight : Double, height:Double)
    {
        val height2 = height/100
        val bmiValue : Double= weight/(height2*height2)
        bmi.text = bmiValue.toString()

        if(bmiValue<25 && bmiValue>18.5)
        {
            statment.text = "You're Healthy"
            statment.setTextColor(Color.GREEN)
        }
        else{
            statment.text = "You're Not Healthy"
            statment.setTextColor(Color.RED)
        }

    }

    override fun onPause() {
        super.onPause()
        sfEditor.apply {
            putString("Weight",weight.text.toString())
            putString("Height",height.text.toString())
            putString("bmi",bmi.text.toString())
            apply()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        val tempHeight = sf.getString("Height",null)
        val tempWeight = sf.getString("Weight",null)
        val tempBMI = sf.getString("bmi",null)

        height.setText(tempHeight)
        weight.setText(tempWeight)
        bmi.text = tempBMI

        if(tempBMI.toString().toDouble()<25 && tempBMI.toString().toDouble()>18.5)
        {
            statment.text = "You're Healthy"
            statment.setTextColor(Color.GREEN)
        }
        else{
            statment.text = "You're Not Healthy"
            statment.setTextColor(Color.RED)
        }

    }

}