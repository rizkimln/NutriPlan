package com.example.tugashalamanawal

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Recipe(val name: String, val imageResource: Int)

class RecipeMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe_menu)

        // Apply padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recipe list
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
