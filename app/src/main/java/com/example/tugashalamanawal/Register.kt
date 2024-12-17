package com.example.tugashalamanawal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

class Register : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var usernameuser: EditText
    private lateinit var emailuser: EditText
    private lateinit var passworduser: EditText
    private lateinit var namaLengkapUser: EditText
    private lateinit var tanggalLahirUser: EditText
    private lateinit var battonmasuk: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Inisialisasi elemen UI
        usernameuser = findViewById(R.id.editUsername)
        emailuser = findViewById(R.id.editEmail)
        passworduser = findViewById(R.id.editPassword)
        namaLengkapUser = findViewById(R.id.editNamaLengkap)
        tanggalLahirUser = findViewById(R.id.editTanggalLahir)
        battonmasuk = findViewById(R.id.btnMasuk)

        // Inisialisasi Firebase Auth dan Firestore
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Aksi tombol masuk
        battonmasuk.setOnClickListener {
            val email = emailuser.text.toString().trim()
            val password = passworduser.text.toString().trim()
            val username = usernameuser.text.toString().trim()
            val namaLengkap = namaLengkapUser.text.toString().trim()
            val tanggalLahir = tanggalLahirUser.text.toString().trim()

            // Validasi input dasar
            if (email.isEmpty() || password.isEmpty() || username.isEmpty() || namaLengkap.isEmpty() || tanggalLahir.isEmpty()) {
                Toast.makeText(applicationContext, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi email
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(applicationContext, "Format email tidak valid!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi password
            if (password.length < 6) {
                Toast.makeText(applicationContext, "Password harus minimal 6 karakter!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi dan konversi tanggal lahir
            val timestampTanggalLahir = getTimestampFromDate(tanggalLahir)
            if (timestampTanggalLahir == null) {
                Toast.makeText(applicationContext, "Format tanggal lahir harus yyyy-MM-dd!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses registrasi Firebase Authentication
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Refresh instance user untuk memastikan pengguna saat ini tidak null
                        firebaseAuth.currentUser?.reload()?.addOnCompleteListener {
                            val userId = firebaseAuth.currentUser?.uid

                            if (userId != null) {
                                // Data user untuk Firestore
                                val user = hashMapOf(
                                    "username" to username,
                                    "email" to email,
                                    "nama_lengkap" to namaLengkap,
                                    "tanggal_lahir" to timestampTanggalLahir // Simpan sebagai Timestamp
                                )

                                // Simpan data ke Firestore
                                firestore.collection("users").document(userId)
                                    .set(user)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            applicationContext,
                                            "Registrasi berhasil",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        startActivity(Intent(this, Login::class.java))
                                        finish()

                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(
                                            applicationContext,
                                            "Gagal menyimpan data: ${e.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "User ID tidak ditemukan setelah registrasi!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        val errorMessage = task.exception?.message ?: "Registrasi gagal"
                        Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    // Fungsi untuk mengonversi string tanggal ke Timestamp
    private fun getTimestampFromDate(date: String): Timestamp? {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            sdf.isLenient = false // Non-lenient untuk validasi ketat
            val parsedDate = sdf.parse(date)
            parsedDate?.let { Timestamp(it) }
        } catch (e: java.text.ParseException) {
            null
        }
    }
}

