package com.example.tugashalamanawal

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recyclerViewNearestGym: RecyclerView
    private lateinit var gymAdapter: GymAdapter
    private lateinit var imageButton: ImageButton
    private lateinit var programBulking: LinearLayout
    private lateinit var programDivisitKalori: LinearLayout
    private lateinit var resepMakanan: LinearLayout
    private lateinit var welcomeTextView: TextView
    private lateinit var kalkulatorBMI: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inisialisasi Firebase
        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        // Inisialisasi View
        welcomeTextView = findViewById(R.id.welcomeTextView)
        imageButton = findViewById(R.id.imageButton)
        programBulking = findViewById(R.id.programBulking)
        programDivisitKalori = findViewById(R.id.programDivisitKalori)
        resepMakanan = findViewById(R.id.ResepMakanan)
        kalkulatorBMI = findViewById(R.id.KalkulatorBMI)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set listener untuk tombol
        programBulking.setOnClickListener {
            val intent = Intent(this, DataBulking::class.java)
            startActivity(intent)
        }

        kalkulatorBMI.setOnClickListener{
            val intent = Intent(this,KalkulatorActivity::class.java)
            startActivity(intent)
        }

        programDivisitKalori.setOnClickListener {
            val intent = Intent(this, DataDiet::class.java)
            startActivity(intent)
        }

        imageButton.setOnClickListener {
            val intent = Intent(this, ProfilUser::class.java)
            startActivity(intent)
        }

        resepMakanan.setOnClickListener {
            val intent = Intent(this, RecipeMenuActivity::class.java)
            startActivity(intent)
        }



        // Muat nama pengguna
        loadUserName()

        // Konfigurasi RecyclerView
        recyclerViewCategory()
    }

    private fun loadUserName() {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val username = document.getString("username") ?: "User"
                        welcomeTextView.text = "Hallo $username"
                    } else {
                        welcomeTextView.text = "Hallo"
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Gagal memuat nama pengguna: ${exception.message}", Toast.LENGTH_SHORT).show()
                    welcomeTextView.text = "Hallo"
                }
        } else {
            welcomeTextView.text = "Hallo"
        }
    }

    private fun recyclerViewCategory() {
        recyclerViewNearestGym = findViewById(R.id.recyclerViewNearestGym)
        recyclerViewNearestGym.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val gymList = mutableListOf<Gym>()
        gymAdapter = GymAdapter(gymList) { gym ->
            val intent = Intent(this, NearestGYM::class.java)
            intent.putExtra("gym_name", gym.name) // Kirim nama gym
            intent.putExtra("gym_email", gym.email) // Kirim email gym
            intent.putExtra("gym_contact", gym.contact) // Kirim kontak gym
            startActivity(intent)
        }
        recyclerViewNearestGym.adapter = gymAdapter

        val db = FirebaseFirestore.getInstance()
        db.collection("gyms")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val displayName = document.getString("display_name") ?: "Unknown Gym"
                    val name = document.getString("name") ?: "Unknown Gym"
                    val email = document.getString("email") ?: "Unknown Email"
                    val contact = document.getString("contact") ?: "Unknown Contact" // Tambahkan pengambilan contact
                    val image = R.drawable.gambar_class_online // Gambar statis

                    // Gunakan gambar statis secara langsung
                    gymList.add(Gym(displayName, image, name, email, contact))
                }
                gymAdapter.notifyDataSetChanged() // Perbarui adapter setelah data berhasil dimuat
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to load gyms: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
