package com.example.tugashalamanawal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class DataBulking : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var btnSubmit: Button
    private lateinit var rgGender: RadioGroup
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var spinnerID: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_bulking)

        // Inisialisasi Firebase dan komponen UI
        db = FirebaseFirestore.getInstance()
        btnSubmit = findViewById(R.id.btnSubmit)
        rgGender = findViewById(R.id.rgGender)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)
        spinnerID = findViewById(R.id.spinnerID)

        // Load data untuk Spinner dari Firebase
        loadSpinnerData()

        // Tombol submit untuk memulai pengambilan data
        btnSubmit.setOnClickListener {
            val selectedGenderId = rgGender.checkedRadioButtonId
            val selectedGender = when (selectedGenderId) {
                R.id.rbMale -> "Laki-laki" // Nama subkoleksi
                R.id.rbFemale -> "Perempuan" // Nama subkoleksi
                else -> null
            }

            val documentID = spinnerID.selectedItem?.toString() ?: "" // Ambil item yang dipilih

            if (selectedGender != null && documentID.isNotEmpty()) {
                loadDocumentNames(documentID, selectedGender)
            } else {
                Toast.makeText(this, "Pilih jenis kelamin dan ID yang valid.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Fungsi untuk mengisi Spinner dengan data dari Firebase
     */
    private fun loadSpinnerData() {
        db.collection("BMI")
            .get()
            .addOnSuccessListener { documents ->
                val documentIDs = mutableListOf<String>()
                for (document in documents) {
                    documentIDs.add(document.id) // Tambahkan ID dokumen ke daftar
                }

                if (documentIDs.isNotEmpty()) {
                    val adapter = ArrayAdapter(
                        this,
                        android.R.layout.simple_spinner_item,
                        documentIDs
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerID.adapter = adapter
                } else {
                    Toast.makeText(this, "Tidak ada data untuk Spinner.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseError", "Error fetching Spinner data: ${e.message}")
                Toast.makeText(this, "Gagal memuat data Spinner.", Toast.LENGTH_SHORT).show()
            }
    }

    /**
     * Fungsi untuk mengambil semua nama dokumen dari subkoleksi (Laki-laki/Perempuan)
     */
    private fun loadDocumentNames(documentID: String, gender: String) {
        db.collection("BMI")
            .document(documentID)
            .collection(gender)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val documentNames = mutableListOf<String>()
                    for (document in documents) {
                        documentNames.add(document.id) // Ambil nama dokumen (ID dokumen)
                    }

                    Log.d("DataBulking", "Document Names: $documentNames") // Log dokumen yang ditemukan

                    // Kirim nama dokumen ke halaman berikutnya
                    val intent = Intent(this, misiHarian::class.java)
                    intent.putStringArrayListExtra("documentNames", ArrayList(documentNames))
                    intent.putExtra("documentID", documentID)
                    intent.putExtra("gender", gender)
                    startActivity(intent)
                } else {
                    Log.w("DataBulking", "No documents found in subcollection.")
                    Toast.makeText(this, "Tidak ada dokumen di subkoleksi.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FirebaseData", "Error fetching data: ${exception.message}")
                Toast.makeText(this, "Gagal memuat data: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
