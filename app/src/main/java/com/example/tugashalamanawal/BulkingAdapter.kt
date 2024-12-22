package com.example.tugashalamanawal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class BulkingAdapter(
    private val bulkingList: List<BulkingItem>,
    private val onItemClick: (BulkingItem) -> Unit
) : RecyclerView.Adapter<BulkingAdapter.BulkingViewHolder>() {

    class BulkingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textDay: TextView = itemView.findViewById(R.id.textDay)
//        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        val imageFood: ImageView = itemView.findViewById(R.id.imageFood)
//        val textSelesai: TextView = itemView.findViewById(R.id.textSelesai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BulkingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return BulkingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BulkingViewHolder, position: Int) {
        val bulkingItem = bulkingList[position]
        holder.textDay.text = bulkingItem.dayNumber
//        holder.textDescription.text = bulkingItem.description
        holder.imageFood.setImageResource(bulkingItem.imageResource)

//        if (bulkingItem.isCompleted) {
//            holder.textSelesai.visibility = View.VISIBLE
//        } else {
//            holder.textSelesai.visibility = View.GONE
//        }

        holder.itemView.setOnClickListener {
            onItemClick(bulkingItem)
        }
    }

    override fun getItemCount(): Int = bulkingList.size
}
