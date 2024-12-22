package com.example.tugashalamanawal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class RecipeMenuActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchField: EditText
    private lateinit var recipeAdapter: RecipeAdapter
    private val recipeList = mutableListOf<Recipe>() // Original recipe list
    private val filteredRecipeList = mutableListOf<Recipe>() // Filtered list for display

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_menu)

        // Initialize RecyclerView and Search Field
        recyclerView = findViewById(R.id.recipeRecyclerView)
        searchField = findViewById(R.id.searchField)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recipeAdapter = RecipeAdapter(filteredRecipeList) { selectedRecipe ->
            // Launch RecipeDetailActivity and pass data
            val intent = Intent(this, RecipeDetailActivity::class.java).apply {
                putExtra("RECIPE_NAME", selectedRecipe.name)
                putExtra("RECIPE_IMAGE", selectedRecipe.imageResource)
            }
            startActivity(intent)
        }
        recyclerView.adapter = recipeAdapter

        // Fetch data from Firestore
        fetchRecipesFromFirestore()

        // Set up search functionality
        searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterRecipes(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun fetchRecipesFromFirestore() {
        val db = FirebaseFirestore.getInstance()

        db.collection("Menu") // Firestore collection name
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val name = document.id
                    val imageName = document.getString("image") ?: "placeholder"
                    val resId = resources.getIdentifier(imageName, "drawable", packageName)

                    // Add recipe to the main list
                    recipeList.add(Recipe(name, resId))
                }
                filteredRecipeList.addAll(recipeList) // Initially display all recipes
                recipeAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun filterRecipes(query: String) {
        filteredRecipeList.clear()
        if (query.isEmpty()) {
            filteredRecipeList.addAll(recipeList) // Show all recipes if query is empty
        } else {
            val lowerCaseQuery = query.lowercase()
            filteredRecipeList.addAll(
                recipeList.filter { recipe ->
                    recipe.name.lowercase().contains(lowerCaseQuery)
                }
            )
        }
        recipeAdapter.notifyDataSetChanged()
    }
}
