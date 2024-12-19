package com.example.tugashalamanawal

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class KalkulatorActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kalkulator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etWeight = findViewById<EditText>(R.id.etWeight)
        val etAge = findViewById<EditText>(R.id.etAge)
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnCalculate.setOnClickListener {
            val heightText = etHeight.text.toString()
            val weightText = etWeight.text.toString()
            val ageText = etAge.text.toString()
            val selectedGenderId = rgGender.checkedRadioButtonId

            if (heightText.isNotEmpty() && weightText.isNotEmpty() && ageText.isNotEmpty() && selectedGenderId != -1) {
                val height = heightText.toDouble() / 100 // Convert cm to meters
                val weight = weightText.toDouble()
                val age = ageText.toInt()
                val gender = when (selectedGenderId) {
                    R.id.rbMale -> "Male"
                    R.id.rbFemale -> "Female"
                    else -> "Unknown"
                }

                // BMI Calculation
                val bmi = weight / (height * height)

                // Display BMI with gender and age info
                tvResult.text = String.format(
                    "Your BMI is: %.2f\nGender: %s\nAge: %d years",
                    bmi, gender, age
                )
            } else {
                tvResult.text = "Please fill all fields and select gender."
            }
        }
    }
}