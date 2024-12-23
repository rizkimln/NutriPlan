package com.example.tugashalamanawal

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class DetailBulking : AppCompatActivity() {

    private lateinit var headerTitle: TextView
    private lateinit var sarapanContent: TextView
    private lateinit var makanSiangContent: TextView
    private lateinit var makanMalamContent: TextView
    private lateinit var cemilanContent: TextView
    private lateinit var olahragaContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_bulking)

        // Inisialisasi Views
        val backButton = findViewById<ImageView>(R.id.backButton)
        headerTitle = findViewById(R.id.headerTitle)
        sarapanContent = findViewById(R.id.sarapanContent)
        makanSiangContent = findViewById(R.id.makanSiangContent)
        makanMalamContent = findViewById(R.id.makanMalamContent)
        cemilanContent = findViewById(R.id.cemilanContent)
        olahragaContent = findViewById(R.id.olahragaContent)

        backButton.setOnClickListener {
            finish()
        }
        // Ambil data dari intent
        val dayNumber = intent.getStringExtra("dayNumber")
        val data = intent.getSerializableExtra("data") as? Map<String, Any>

        // Update UI
        if (dayNumber != null) {
            headerTitle.text = dayNumber
        }

        data?.let {
            sarapanContent.text = (it["Sarapan"] as? List<*>)?.joinToString("\n") { item ->
                "• $item"
            } ?: "Tidak ada data"

            makanSiangContent.text = (it["Makan Siang"] as? List<*>)?.joinToString("\n") { item ->
                "• $item"
            } ?: "Tidak ada data"

            makanMalamContent.text = (it["Makan Malam"] as? List<*>)?.joinToString("\n") { item ->
                "• $item"
            } ?: "Tidak ada data"

            cemilanContent.text = (it["Cemilan"] as? List<*>)?.joinToString("\n") { item ->
                "• $item"
            } ?: "Tidak ada data"

            olahragaContent.text = (it["Olahraga"] as? List<*>)?.joinToString("\n") { item ->
                "• $item"
            } ?: "Tidak ada data"
        }
    }
}
