package com.example.tugashalamanawal

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var descriptionText: TextView
    private lateinit var ingredientsRecyclerView: RecyclerView
    private lateinit var stepsRecyclerView: RecyclerView

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        imageView = findViewById(R.id.recipeImage)
        descriptionText = findViewById(R.id.recipeDescription)
        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView)
        stepsRecyclerView = findViewById(R.id.stepsRecyclerView)

        val recipeName = intent.getStringExtra("RECIPE_NAME")
        val recipeImage = intent.getIntExtra("RECIPE_IMAGE", 0)

        if (recipeImage != 0) {
            imageView.setImageResource(recipeImage)
        } else {
            Log.e("RecipeDetail", "Invalid image resource ID")
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

                    val steps = document.get("Cara Membuat") as? List<String> ?: emptyList()
                    stepsRecyclerView.layoutManager = LinearLayoutManager(this)
                    stepsRecyclerView.adapter = TextListAdapter(steps)
                } else {
                    Log.e("Firestore", "Document not found")
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
