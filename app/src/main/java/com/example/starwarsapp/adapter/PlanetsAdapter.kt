package com.example.starwarsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.ItemPlanetsBinding

data class Planets(
    val name: String,
    val imageResId: Int,
    val description: String
)

class PlanetsAdapter(
    private val planets: List<Planets>,
    private val onClick: (Planets) -> Unit
) : RecyclerView.Adapter<PlanetsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemPlanetsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(planets: Planets, onClick: (Planets) -> Unit) {
            binding.planetsName.text = planets.name
            binding.planetsImage.setImageResource(planets.imageResId)
            binding.root.setOnClickListener {
                onClick(planets)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlanetsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(planets[position], onClick)
    }

    override fun getItemCount() = planets.size
}
