package com.example.tugashalamanawal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugashalamanawal.GymAdapter
import com.example.tugashalamanawal.Gym

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewNearestGym: RecyclerView
    private lateinit var gymAdapter: GymAdapter
    private  lateinit var imageButton: ImageButton
    private  lateinit var  programBulking : LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        imageButton=findViewById(R.id.imageButton)
        programBulking=findViewById(R.id.programBulking)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, Register::class.java))
        })

        // Set listener untuk "programBulking"
        programBulking.setOnClickListener {
            // Intent untuk membuka BulkingActivity
            val intent = Intent(this, Bulking::class.java)
            startActivity(intent)
        }



        // Panggil fungsi recyclerViewCategory untuk setup RecyclerView
        recyclerViewCategory()
    }

    private fun recyclerViewCategory() {
        // Data dummy untuk gym
        val gymList = listOf(
            Gym("Gym A", R.drawable.gsys),
            Gym("Gym B", R.drawable.ehat),
            Gym("Gym C", R.drawable.fh2),
            Gym("Gym D", R.drawable.abab)
        )

        // Inisialisasi RecyclerView dan Adapter
        recyclerViewNearestGym = findViewById(R.id.recyclerViewNearestGym)
        recyclerViewNearestGym.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        gymAdapter = GymAdapter(gymList) { gym ->
            // Aksi ketika item di klik
            showGymDetails(gym)
        }
        recyclerViewNearestGym.adapter = gymAdapter
    }

    private fun showGymDetails(gym: Gym) {
        when (gym.name) {
            "Gym A" -> {
                // Navigasi ke GymADetailActivity
                val intent = Intent(this, NearestGYM::class.java)
                intent.putExtra("gym_name", gym.name) // Kirim data jika diperlukan
                startActivity(intent)
            }
            "Gym B" -> {
                // Tambahkan navigasi untuk Gym B jika diperlukan
            }
            // Tambahkan else atau case untuk gym lainnya
            else -> {
                Toast.makeText(this, "Gym not available", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
