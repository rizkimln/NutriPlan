package com.example.tugashalamanawal

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BulkingActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bulkingAdapter: BulkingAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bulking)

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recipeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Data for Bulking Days
        val bulkingItems = listOf(
            BulkingItem(1, R.drawable.beef_brokoli, true),
            BulkingItem(2, R.drawable.beef_bulgogi, true),
            BulkingItem(3, R.drawable.beef_teriyaki, true),
            BulkingItem(4, R.drawable.beef_burger, false),
            BulkingItem(5, R.drawable.beef_meatbowl, false),
            BulkingItem(6, R.drawable.beef_sate, false)
        )

        bulkingAdapter = BulkingAdapter(bulkingItems)
        recyclerView.adapter = bulkingAdapter
    }
}
