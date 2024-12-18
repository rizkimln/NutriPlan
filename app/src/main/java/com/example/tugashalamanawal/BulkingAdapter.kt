package com.example.tugashalamanawal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BulkingAdapter(private val bulkingList: List<BulkingItem>) :
    RecyclerView.Adapter<BulkingAdapter.BulkingViewHolder>() {

    class BulkingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textDay: TextView = itemView.findViewById(R.id.textDay)
        val imageFood: ImageView = itemView.findViewById(R.id.imageFood)
        val textSelesai: TextView = itemView.findViewById(R.id.textSelesai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BulkingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return BulkingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BulkingViewHolder, position: Int) {
        val bulkingItem = bulkingList[position]
        holder.textDay.text = "Hari ke ${bulkingItem.dayNumber}"
        holder.imageFood.setImageResource(bulkingItem.imageResource)

        // Show or hide 'Selesai' indicator
        if (bulkingItem.isCompleted) {
            holder.textSelesai.visibility = View.VISIBLE
        } else {
            holder.textSelesai.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = bulkingList.size
}
