package com.example.tugashalamanawal

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var imageView: ImageView
    private lateinit var descriptionText: TextView
    private lateinit var ingredientsRecyclerView: RecyclerView
    private lateinit var stepsRecyclerView: RecyclerView

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        // Initialize Views
        backButton = findViewById(R.id.backButton)
        imageView = findViewById(R.id.recipeImage)
        descriptionText = findViewById(R.id.recipeDescription)
        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView)
        stepsRecyclerView = findViewById(R.id.stepsRecyclerView)

        // Back Button Functionality
        backButton.setOnClickListener {
            finish() // Close the current activity and go back to the previous screen
        }

        // Set Nested Scrolling for RecyclerView
        ingredientsRecyclerView.isNestedScrollingEnabled = false
        stepsRecyclerView.isNestedScrollingEnabled = false

        // Fetch Recipe Details
        val recipeName = intent.getStringExtra("RECIPE_NAME")
        val recipeImage = intent.getIntExtra("RECIPE_IMAGE", 0)

        if (recipeImage != 0) {
            imageView.setImageResource(recipeImage)
        } else {
            Toast.makeText(this, "Invalid image resource ID", Toast.LENGTH_SHORT).show()
        }

        descriptionText.text = recipeName

        if (recipeName != null) {
            fetchRecipeDetails(recipeName)
        } else {
            Toast.makeText(this, "Recipe name is missing!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchRecipeDetails(recipeName: String) {
        db.collection("Menu")
            .document(recipeName)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val description = document.getString("Deskripsi") ?: "No description available"
                    descriptionText.text = description

                    val ingredients = document.get("Bahan") as? List<String> ?: emptyList()
                    ingredientsRecyclerView.layoutManager = LinearLayoutManager(this)
                    ingredientsRecyclerView.adapter = TextListAdapter(ingredients)

                    val steps = document.get("Cara Membuat") as? List<String>
                        ?: document.get("Cara Memasak") as? List<String>
                        ?: emptyList()

                    stepsRecyclerView.layoutManager = LinearLayoutManager(this)
                    stepsRecyclerView.adapter = TextListAdapter(steps)

                    if (steps.isEmpty()) {
                        Toast.makeText(this, "No steps available for this recipe.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Document not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
