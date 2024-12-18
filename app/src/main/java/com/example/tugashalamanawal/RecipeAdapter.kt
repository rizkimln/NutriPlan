package com.example.tugashalamanawal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(
    private val recipeList: List<Recipe>,
    private val onRecipeClick: (Recipe) -> Unit // Callback for clicks
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeImage: ImageView = itemView.findViewById(R.id.recipeImage)
        val recipeName: TextView = itemView.findViewById(R.id.recipeName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.recipeName.text = recipe.name
        holder.recipeImage.setImageResource(recipe.imageResource)

        // Set click listener
        holder.itemView.setOnClickListener {
            onRecipeClick(recipe) // Trigger callback
        }
    }

    override fun getItemCount(): Int = recipeList.size
}
