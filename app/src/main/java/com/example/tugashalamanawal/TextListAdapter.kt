package com.example.tugashalamanawal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TextListAdapter(private val items: List<String>) :
    RecyclerView.Adapter<TextListAdapter.TextViewHolder>() {

    // ViewHolder untuk menyimpan referensi ke elemen layout
    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.itemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_text, parent, false)
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.textView.text = items[position] // Set teks untuk setiap item
    }

    override fun getItemCount() = items.size
}
