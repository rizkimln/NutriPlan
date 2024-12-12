package com.example.tugashalamanawal

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class EditProfilActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var editIcon: ImageView
    private lateinit var backButton: ImageView
    private lateinit var birthDateInput: EditText
    private lateinit var emailInput: EditText

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profil)

        profileImage = findViewById(R.id.profile_image)
        editIcon = findViewById(R.id.edit_icon)
        backButton = findViewById(R.id.back_button)
        birthDateInput = findViewById(R.id.tanggal_lahir_input)
        emailInput = findViewById(R.id.email_field)

        // Set up click listener on the edit icon
        editIcon.setOnClickListener {
            openImagePicker()
        }

        // Navigate back to ProfilActivity
        backButton.setOnClickListener {
            finish() // Closes the current activity and returns to the previous one
        }

        // Show DatePickerDialog when clicking the birth date field
        birthDateInput.setOnClickListener {
            showDatePicker()
        }

        // Validate email input
        emailInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { // When focus is lost
                validateEmail()
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                birthDateInput.setText(date)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun validateEmail() {
        val email = emailInput.text.toString()
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Invalid email address"
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            if (selectedImageUri != null) {
                profileImage.setImageURI(selectedImageUri) // Set the selected image to the profile image
                Toast.makeText(this, "Profile picture updated!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to load image.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
