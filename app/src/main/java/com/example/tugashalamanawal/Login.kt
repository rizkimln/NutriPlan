package com.example.tugashalamanawal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var usernameuser : EditText
    private lateinit var emailuser : EditText
    private lateinit var passworduser : EditText
    private lateinit var battonmasuk : Button
    private lateinit var battonregistrasi : Button

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

        battonregistrasi.setOnClickListener {
            // Intent untuk membuka BulkingActivity
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        battonmasuk.setOnClickListener(View.OnClickListener {
            val email = emailuser.text.toString().trim()
            val password = passworduser.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "ga boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) { // Periksa apakah autentikasi berhasil
                            Toast.makeText(applicationContext, "berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            // Tangkap dan tampilkan error jika login gagal
                            val errorMessage = task.exception?.message ?: "Login gagal"
                            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        })

    }
}