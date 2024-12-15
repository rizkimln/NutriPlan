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

class Register : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var usernameuser : EditText
    private lateinit var emailuser : EditText
    private lateinit var passworduser : EditText
    private lateinit var battonmasuk : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

//        usernameuser = findViewById(R.id.editUsername)
        emailuser = findViewById(R.id.editEmail)
        passworduser = findViewById(R.id.editPassword)
        battonmasuk = findViewById(R.id.btnMasuk)
        firebaseAuth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        battonmasuk.setOnClickListener(View.OnClickListener {
            val email = emailuser.text.toString().trim()
            val password = passworduser.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "ga boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) { // Periksa apakah sukses
                            Toast.makeText(applicationContext, "berhasil", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, Login::class.java))
                        } else {
                            // Tangkap kesalahan dan tampilkan pesan
                            val errorMessage = task.exception?.message ?: "gagal"
                            Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        })

    }



}