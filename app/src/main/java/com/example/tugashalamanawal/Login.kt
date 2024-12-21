package com.example.tugashalamanawal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class Login : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var emailuser: EditText
    private lateinit var passworduser: EditText
    private lateinit var battonmasuk: Button
    private lateinit var battonregistrasi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        emailuser = findViewById(R.id.editEmail)
        passworduser = findViewById(R.id.editPassword)
        battonmasuk = findViewById(R.id.btnMasuk)
        battonregistrasi = findViewById(R.id.btnRegistrasi)
        firebaseAuth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol registrasi
        battonregistrasi.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        // Tombol login
        battonmasuk.setOnClickListener {
            val email = emailuser.text.toString().trim()
            val password = passworduser.text.toString().trim()

            // Pengecekan awal
            if (email.isEmpty()) {
                emailuser.error = "Email tidak boleh kosong."
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passworduser.error = "Password tidak boleh kosong."
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailuser.error = "Format email tidak valid."
                return@setOnClickListener
            }

            // Proses login
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login berhasil
                        Toast.makeText(applicationContext, "Berhasil masuk", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        // Tangkap kesalahan login
                        val exception = task.exception
                        if (exception != null) {
                            Log.e("FirebaseAuthError", "Exception class: ${exception::class.java}")
                            Log.e("FirebaseAuthError", "Error message: ${exception.message}")

                            when (exception) {
                                is FirebaseAuthInvalidUserException -> {
                                    // Email tidak ditemukan
                                    emailuser.error = "Email tidak ditemukan. Silakan daftar terlebih dahulu."
                                }
//                                is FirebaseAuthInvalidCredentialsException -> {
//                                    // Password salah
//                                    passworduser.error = "Password salah. Silakan coba lagi."
//                                }
                                else -> {
                                    // Kesalahan lainnya
                                    Toast.makeText(
                                        applicationContext,
                                        exception.message ?: "Login gagal. Silakan coba lagi.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(applicationContext, "Login gagal. Kesalahan tidak diketahui.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }
}
