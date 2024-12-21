package com.example.tugashalamanawal

import android.os.Bundle
import android.util.Log
import android.view.View // Tambahkan impor ini untuk mengatasi error
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DataDiet : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private val TAG = "DataDietActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_diet)

        // Debugging: Memastikan layout dimuat
        Log.d(TAG, "Layout activity_data_diet berhasil dimuat")

        // Inisialisasi referensi database
        database = FirebaseDatabase.getInstance().reference

        // Deklarasi elemen view
        val inputAge = findViewById<EditText>(R.id.etID)
        val inputHeight = findViewById<EditText>(R.id.input_height)
        val inputWeight = findViewById<EditText>(R.id.input_weight)
        val startButton = findViewById<Button>(R.id.btnSubmit)

        // Atur padding untuk elemen utama agar menyesuaikan dengan sistem bar
        val mainView = findViewById<View>(R.id.dataDiet)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        // Tambahkan listener pada tombol "Mulai"
        startButton.setOnClickListener {
            val age = inputAge.text.toString().trim()
            val height = inputHeight.text.toString().trim()
            val weight = inputWeight.text.toString().trim()

            Log.d(TAG, "Input Age: $age, Height: $height, Weight: $weight")

            if (age.isEmpty() || height.isEmpty() || weight.isEmpty()) {
                Toast.makeText(this, "Harap isi semua data!", Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Data tidak lengkap. Harap isi semua field.")
            } else {
                saveDataToFirebase(age, height, weight)
            }
        }
    }

    private fun saveDataToFirebase(age: String, height: String, weight: String) {
        val data = mapOf(
            "age" to age,
            "height" to height,
            "weight" to weight
        )

        val dataId = database.child("dietData").push().key

        if (dataId != null) {
            database.child("dietData").child(dataId).setValue(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "Data berhasil disimpan dengan ID: $dataId")
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Gagal menyimpan data: ${exception.message}", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Error saat menyimpan data: ${exception.message}")
                }
        } else {
            Toast.makeText(this, "Terjadi kesalahan, coba lagi!", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Gagal membuat ID unik untuk data.")
        }
    }
}