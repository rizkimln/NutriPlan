package com.example.tugashalamanawal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GymAdapter(
    private val gymList: List<Gym>,
    private val onItemClick: (Gym) -> Unit
) : RecyclerView.Adapter<GymAdapter.GymViewHolder>() {

    inner class GymViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgGym: ImageView = itemView.findViewById(R.id.imgGym)
        val txtGymName: TextView = itemView.findViewById(R.id.txtGymName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nearest_gym, parent, false)
        return GymViewHolder(view)
    }

    override fun onBindViewHolder(holder: GymViewHolder, position: Int) {
        val gym = gymList[position]
        holder.imgGym.setImageResource(gym.imageResource)
        holder.txtGymName.text = gym.displayName

        // Handle klik item
        holder.itemView.setOnClickListener {
            onItemClick(gym)
        }
    }

    override fun getItemCount(): Int {
        return gymList.size
    }
}
