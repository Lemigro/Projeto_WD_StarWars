package com.example.starwarsapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.starwarsapp.R
import com.example.starwarsapp.databinding.ItemSpeciesBinding
import com.example.starwarsapp.model.SpeciesModel

class SpeciesAdapter(
    private val speciesList: List<SpeciesModel>,
    private val onClick: (SpeciesModel) -> Unit
) : RecyclerView.Adapter<SpeciesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemSpeciesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(species: SpeciesModel, onClick: (SpeciesModel) -> Unit) {
            binding.speciesName.text = species.name

            Glide.with(binding.speciesImage.context)
                .load(species.url?.let { getImageUrl(it) })
                .placeholder(R.drawable.placeholder)
                .into(binding.speciesImage)

            binding.root.setOnClickListener {
                onClick(species)
            }
        }

        private fun getImageUrl(url: String): String {
            val id = url.trimEnd('/').split("/").last()
            Log.d("SpeciesAdapter", "ID da espécie: $id")
            return "https://starwars-visualguide.com/assets/img/species/$id.jpg"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSpeciesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(speciesList[position], onClick)
    }

    override fun getItemCount(): Int = speciesList.size
}
