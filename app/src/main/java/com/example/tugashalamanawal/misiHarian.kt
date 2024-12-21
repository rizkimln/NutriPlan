package com.example.tugashalamanawal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class misiHarian : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MisiHarianAdapter
    private lateinit var db: FirebaseFirestore
    private val dataList = mutableListOf<Map<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_misi_harian)

        recyclerView = findViewById(R.id.recipeRecyclerView)
        db = FirebaseFirestore.getInstance()

        // Ambil data dari intent
        val documentID = intent.getStringExtra("documentID")
        val gender = intent.getStringExtra("gender")

        if (documentID != null && gender != null) {
            loadData(documentID, gender)
        } else {
            Toast.makeText(this, "Data tidak ditemukan.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData(documentID: String, gender: String) {
        db.collection("BMI")
            .document(documentID)
            .collection(gender)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val data = document.data.toMutableMap()
                    data["dayNumber"] = document.id // Pastikan dayNumber ditambahkan
                    dataList.add(data)
                }

                // Urutkan data berdasarkan angka yang ada di dalam `dayNumber`
                dataList.sortBy { extractDayNumber(it["dayNumber"].toString()) }

                // Atur RecyclerView
                adapter = MisiHarianAdapter(dataList) { dayNumber, data ->
                    navigateToDetail(dayNumber, data)
                }
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)
            }
            .addOnFailureListener { e ->
                Log.e("misiHarian", "Error loading data: ${e.message}")
            }
    }

    // Fungsi untuk mengekstrak angka dari string `Hari ke X`
    private fun extractDayNumber(dayNumber: String): Int {
        return dayNumber.replace("[^\\d]".toRegex(), "").toIntOrNull() ?: 0
    }



    private fun navigateToDetail(dayNumber: String, data: Map<String, Any>) {
        val intent = Intent(this, DetailBulking::class.java)
        intent.putExtra("dayNumber", dayNumber)
        intent.putExtra("data", HashMap(data)) // Kirim data detail
        startActivity(intent)
    }
}
