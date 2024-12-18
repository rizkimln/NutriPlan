package com.example.tugashalamanawal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeDetailAdapter(private val sections: List<RecipeSection>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return sections[position].viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.IMAGE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recipe_image, parent, false)
                ImageViewHolder(view)
            }
            ViewType.TITLE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recipe_title, parent, false)
                TitleViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recipe_text, parent, false)
                TextViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val section = sections[position]
        when (holder) {
            is ImageViewHolder -> {
                // Replace imageUrl with drawable resource name passed as content
                val resId = holder.itemView.context.resources.getIdentifier(
                    section.content, "drawable", holder.itemView.context.packageName
                )
                holder.imageView.setImageResource(resId)
            }
            is TitleViewHolder -> holder.titleText.text = section.content
            is TextViewHolder -> holder.contentText.text = section.content
        }
    }

    override fun getItemCount() = sections.size

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.recipeImage)
    }

    class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.recipeTitle)
    }

    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentText: TextView = view.findViewById(R.id.recipeText)
    }
}
