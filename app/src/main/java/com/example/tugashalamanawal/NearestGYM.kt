package com.example.tugashalamanawal

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class NearestGYM : AppCompatActivity() {

    private lateinit var textViewName: TextView
    private lateinit var textViewContact: TextView
    private lateinit var textViewEmail: TextView
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearest_gym)

        // Inisialisasi TextView
        textViewName = findViewById(R.id.textViewName)
        textViewContact = findViewById(R.id.textViewContact)
        textViewEmail = findViewById(R.id.textViewEmail)

        // Ambil nama gym yang dikirimkan melalui intent
        val gymName = intent.getStringExtra("gym_name") // Nama yang dikirim dari halaman utama

        // Ambil data Firestore berdasarkan nama gym
        fetchGymDetails(gymName)
    }

    private fun fetchGymDetails(gymName: String?) {
        db.collection("gyms")
            .whereEqualTo("name", gymName) // Gunakan field "name" sebagai parameter pencarian
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Tampilkan detail dari Firestore
                    textViewName.text = "Name: ${document.getString("name")}"
                    textViewContact.text = "Contact: ${document.getString("contact")}"
                    textViewEmail.text = "Email: ${document.getString("email")}"
                }
            }
            .addOnFailureListener { exception ->
                textViewName.text = "Error: ${exception.message}" // Tampilkan error jika gagal
            }
    }
}
