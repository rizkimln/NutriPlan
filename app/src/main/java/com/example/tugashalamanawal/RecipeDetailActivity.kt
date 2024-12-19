package com.example.tugashalamanawal

import android.os.Bundle
import android.util.Log
import android.view.View
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

        // Inisialisasi Views
        imageView = findViewById(R.id.recipeImage)
        descriptionText = findViewById(R.id.recipeDescription)
        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView)
        stepsRecyclerView = findViewById(R.id.stepsRecyclerView)

        // Setel Nested Scrolling agar RecyclerView tidak scroll secara independen
        ingredientsRecyclerView.isNestedScrollingEnabled = false
        stepsRecyclerView.isNestedScrollingEnabled = false

        // Ambil data dari intent
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

                    // Mengambil langkah dari kedua kemungkinan field
                    val caraMembuat = document.get("Cara Membuat") as? List<String>
                    val caraMemasak = document.get("Cara Memasak") as? List<String>

                    // Prioritaskan langkah yang tersedia
                    val steps = caraMembuat ?: caraMemasak ?: emptyList()

                    // Menampilkan langkah-langkah
                    stepsRecyclerView.layoutManager = LinearLayoutManager(this)
                    stepsRecyclerView.adapter = TextListAdapter(steps)

                    if (steps.isEmpty()) {
                        Toast.makeText(this, "No steps available for this recipe.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("Firestore", "Document not found")
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setRecyclerViewHeightBasedOnChildren(recyclerView: RecyclerView) {
        val adapter = recyclerView.adapter ?: return
        var totalHeight = 0
        for (i in 0 until adapter.itemCount) {
            val holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i))
            adapter.onBindViewHolder(holder, i)
            holder.itemView.measure(
                View.MeasureSpec.makeMeasureSpec(recyclerView.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.UNSPECIFIED
            )
            totalHeight += holder.itemView.measuredHeight
        }

        val params = recyclerView.layoutParams
        params.height = totalHeight
        recyclerView.layoutParams = params
        recyclerView.requestLayout()
    }


}
