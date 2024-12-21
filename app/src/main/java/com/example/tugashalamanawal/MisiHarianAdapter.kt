package com.example.tugashalamanawal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MisiHarianAdapter(
    private val dataList: List<Map<String, Any>>,
    private val onItemClick: (String, Map<String, Any>) -> Unit
) : RecyclerView.Adapter<MisiHarianAdapter.MisiHarianViewHolder>() {

    class MisiHarianViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayNumber: TextView = itemView.findViewById(R.id.textDay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MisiHarianViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MisiHarianViewHolder(view)
    }

    override fun onBindViewHolder(holder: MisiHarianViewHolder, position: Int) {
        val data = dataList[position]
        val dayNumber = data["dayNumber"].toString() // Pastikan key ini ada dalam data
        holder.dayNumber.text = dayNumber

        holder.itemView.setOnClickListener {
            onItemClick(dayNumber, data) // Kirim dayNumber dan data penuh ke callback
        }
    }

    override fun getItemCount(): Int = dataList.size
}




