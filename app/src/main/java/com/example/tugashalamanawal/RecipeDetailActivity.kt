package com.example.tugashalamanawal

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecipeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        // Fetch data passed via Intent
        val recipeName = intent.getStringExtra("RECIPE_NAME")
        val recipeImageRes = intent.getIntExtra("RECIPE_IMAGE", 0)

        // Bind data to views
        val recipeTitle: TextView = findViewById(R.id.recipeTitle)
        val recipeImage: ImageView = findViewById(R.id.recipeImage)

        recipeTitle.text = recipeName
        recipeImage.setImageResource(recipeImageRes)
    }
}
