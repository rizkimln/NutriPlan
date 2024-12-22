package com.example.tugashalamanawal

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

class ProfilUser : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var backButton: ImageView
    private lateinit var logoutButton: LinearLayout
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView
    private lateinit var namaLengkapInput: TextView
    private lateinit var tanggalLahirInput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profil_user)

        // Inisialisasi UI
        profileName = findViewById(R.id.profile_name)
        profileEmail = findViewById(R.id.profile_email)
        namaLengkapInput = findViewById(R.id.nama_lengkap_input)
        tanggalLahirInput = findViewById(R.id.tanggal_lahir_input)

        // Inisialisasi Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        backButton = findViewById(R.id.backButton)


        backButton.setOnClickListener {
            finish()
        }
        logoutButton = findViewById(R.id.logout_setting)
        logoutButton.setOnClickListener {
            val intent = Intent(this, OnboardingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val username = document.getString("username") ?: "Nama Pengguna"
                        val email = document.getString("email") ?: "Email Tidak Diketahui"
                        val namaLengkap = document.getString("nama_lengkap") ?: "Nama Lengkap"
                        val tanggalLahir = document.getTimestamp("tanggal_lahir")?.toDate()?.let {
                            SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(it)
                        } ?: "Tanggal Lahir"

                        // Set nilai ke elemen UI
                        profileName.text = username
                        profileEmail.text = email
                        namaLengkapInput.setText(namaLengkap)
                        tanggalLahirInput.setText(tanggalLahir)
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Gagal mengambil data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User belum login", Toast.LENGTH_SHORT).show()
        }
    }
}
