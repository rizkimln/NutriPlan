package com.example.tugashalamanawal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Recipe(
    val name: String,
    val imageResource: Int
)

class RecipeMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_menu) // Ensure this matches your XML file name

        val recipes = listOf(
            Recipe("Steak Daging Sapi", R.drawable.beef_steak),
            Recipe("Brokoli Sapi", R.drawable.beef_brokoli),
            Recipe("Bulgogi Daging Sapi", R.drawable.beef_bulgogi),
            Recipe("Burger Daging Sapi", R.drawable.beef_burger),
            Recipe("Bakso Daging Sapi", R.drawable.beef_meatbowl),
            Recipe("Sate Daging Sapi", R.drawable.beef_sate),
            Recipe("Teriyaki Daging Sapi", R.drawable.beef_teriyaki),
            Recipe("Wrap Daging Sapi", R.drawable.beef_wrap),
            Recipe("Yakiniku Daging Sapi", R.drawable.beef_yakiniku)
        )

        // Initialize RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recipeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecipeAdapter(recipes)
    }
}
