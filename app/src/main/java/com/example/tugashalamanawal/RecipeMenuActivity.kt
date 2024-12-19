package com.example.tugashalamanawal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast

class   RecipeMenuActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private val recipeList = mutableListOf<Recipe>() // List dinamis untuk menyimpan data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_menu)

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recipeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Adapter kosong saat ini
        recipeAdapter = RecipeAdapter(recipeList) { selectedRecipe ->
            // Launch RecipeDetailActivity and pass data
            val intent = Intent(this, RecipeDetailActivity::class.java).apply {
                putExtra("RECIPE_NAME", selectedRecipe.name)
                putExtra("RECIPE_IMAGE", selectedRecipe.imageResource)
            }
            startActivity(intent)
        }
        recyclerView.adapter = recipeAdapter

        // Ambil data dari Firestore
        fetchRecipesFromFirestore()
    }

    private fun fetchRecipesFromFirestore() {
        val db = FirebaseFirestore.getInstance()

        db.collection("Menu") // Nama koleksi di Firestore
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val name = document.id // Menggunakan ID dokumen sebagai nama resep
                    val imageName = document.getString("image") ?: "placeholder" // Nama gambar
                    val resId = resources.getIdentifier(imageName, "drawable", packageName)

                    // Tambahkan resep ke daftar
                    recipeList.add(Recipe(name, resId))
                }
                recipeAdapter.notifyDataSetChanged() // Update RecyclerView
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
