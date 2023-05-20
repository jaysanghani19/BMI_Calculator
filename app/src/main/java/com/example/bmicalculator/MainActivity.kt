package com.example.bmicalculator

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sf: SharedPreferences
    private lateinit var sfEditor: SharedPreferences.Editor

    private lateinit var height: EditText
    private lateinit var weight: EditText

    private lateinit var bmi: TextView
    private lateinit var statment: TextView

    private lateinit var calculateButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sf = getSharedPreferences("Shared Preference", MODE_PRIVATE)
        sfEditor = sf.edit()
        height = findViewById(R.id.edittext_height)
        weight = findViewById(R.id.edittext_weight)

        bmi = findViewById(R.id.textview_bmi_result)
        statment = findViewById(R.id.textview_healthy)

        calculateButton = findViewById(R.id.button_calculate)
        calculateButton.setOnClickListener {
                calculateBMI(weight.text.toString(), height.text.toString())
        }
    }

    fun setBMI(BMI: Float) {
        bmi.text = BMI.toString()

        if (BMI < 25 && BMI > 18.5) {
            statment.text = "You're Healthy"
            statment.setTextColor(Color.GREEN)
        } else {
            statment.text = "You're Not Healthy"
            statment.setTextColor(Color.RED)
        }
    }

    private fun calculateBMI(weight: String, height: String) {
        if (weight =="" || height=="")
        {
            Toast.makeText(this, "Enter Both Weight and Height", Toast.LENGTH_SHORT).show()
        }
        else{
        val height2 : Float = height.toFloat()/100
        val bmiValue: Float = weight.toFloat() / (height2 * height2)
        setBMI(bmiValue)
        }
    }

    override fun onPause() {
        super.onPause()
        sfEditor.apply {
            putString("Weight", weight.text.toString())
            putString("Height", height.text.toString())
            putFloat("bmi", bmi.text.toString().toFloat())
            apply()
        }
    }

    override fun onResume() {
        super.onResume()
        val tempHeight = sf.getString("Height", " ")
        val tempWeight = sf.getString("Weight", " ")
        val tempBMI : Float= sf.getFloat("bmi", 0.0f)

        height.setText(tempHeight)
        weight.setText(tempWeight)
        setBMI(tempBMI)
    }

}