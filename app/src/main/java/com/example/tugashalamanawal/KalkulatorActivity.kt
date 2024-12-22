package com.example.tugashalamanawal

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kalkulator)

        // Handle edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.KalkulatorBMI)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI components
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etWeight = findViewById<EditText>(R.id.etWeight)
        val etAge = findViewById<EditText>(R.id.etAge)
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        // Set up button click listener
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

                // Determine BMI Category and Warning
                val warning = when {
                    bmi < 18.5 -> "Berat badan kurang"
                    bmi in 18.5..24.9 -> "Berat badan normal"
                    else -> "Berat badan berlebih"
                }

                // Display BMI with gender, age info, and warning
                tvResult.text = String.format(
                    "Your BMI is: %.2f\nGender: %s\nAge: %d years\nWarning: %s",
                    bmi, gender, age, warning
                )
            } else {
                tvResult.text = "Please fill all fields and select gender."
            }
        }
    }
}
