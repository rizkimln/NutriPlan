package com.example.nutriplan

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugashalamanawal.BulkingAdapter
import com.example.tugashalamanawal.BulkingItem
import com.example.tugashalamanawal.R

class BulkingActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bulkingAdapter: BulkingAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bulking)

        //  RecyclerView
        recyclerView = findViewById(R.id.recipeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Data Bulking 30 hari
        val bulkingItems = List(30) { day ->
            BulkingItem(
                dayNumber = day + 1,
                imageResource = when ((day + 1) % 6) {
                    1 -> R.drawable.beef_brokoli
                    2 -> R.drawable.beef_bulgogi
                    3 -> R.drawable.beef_teriyaki
                    4 -> R.drawable.beef_burger
                    5 -> R.drawable.beef_meatbowl
                    else -> R.drawable.beef_sate
                },
                isCompleted = day < 10 // ceritanya udah selesai 10 hari
            )
        }

        // Set up Adapter
        bulkingAdapter = BulkingAdapter(bulkingItems)
        recyclerView.adapter = bulkingAdapter
    }
}
