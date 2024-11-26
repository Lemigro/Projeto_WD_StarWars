package com.example.starwarsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.ItemSpeciesBinding


data class Species(
    val name: String,
    val imageResId: Int,
    val description: String
)

class SpeciesAdapter(
    private val species: List<Species>,
    private val onClick: (Species) -> Unit
) : RecyclerView.Adapter<SpeciesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemSpeciesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(species: Species, onClick: (Species) -> Unit) {
            binding.speciesName.text = species.name
            binding.speciesImage.setImageResource(species.imageResId)
            binding.root.setOnClickListener {
                onClick(species)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSpeciesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(species[position], onClick)
    }

    override fun getItemCount() = species.size
}
