package com.example.tugashalamanawal

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class misiHarian : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var tvHariData: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BulkingAdapter
    private val bulkingList = mutableListOf<BulkingItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_misi_harian)

        // Inisialisasi Views
        backButton = findViewById(R.id.backButton)
        tvHariData = findViewById(R.id.textView8)
        recyclerView = findViewById(R.id.recipeRecyclerView)

        // Dapatkan data dari intent
        val documentNames = intent.getStringArrayListExtra("documentNames")

        if (!documentNames.isNullOrEmpty()) {
            // Buat list item untuk RecyclerView
            for (name in documentNames) {
                bulkingList.add(
                    BulkingItem(
                        dayNumber = name, // Nama dokumen sebagai nama hari
                        description = "Klik untuk melihat detail", // Placeholder deskripsi
                        imageResource = R.drawable.ic_placeholder, // Gambar default
                        isCompleted = false // Default belum selesai
                    )
                )
            }

            // Atur RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = BulkingAdapter(bulkingList) { item ->
                // Aksi klik pada item (opsional)
                Toast.makeText(this, "Klik: ${item.dayNumber}", Toast.LENGTH_SHORT).show()
            }
            recyclerView.adapter = adapter
        } else {
            // Jika tidak ada data diterima
            tvHariData.text = "Tidak ada data yang diterima."
        }

        // Tombol kembali
        backButton.setOnClickListener {
            finish()
        }

        // Update judul halaman
        tvHariData.text = "Misi Harian"
    }
}
