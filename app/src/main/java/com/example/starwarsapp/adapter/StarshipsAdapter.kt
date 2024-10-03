package com.example.starwarsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.ItemStarshipsBinding

data class Starships(
    val name: String,
    val imageResId: Int,
    val description: String
)
class StarshipsAdapter(
    private val starships: List<Starships>,
    private val onClick: (Starships) -> Unit
) : RecyclerView.Adapter<StarshipsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemStarshipsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(starships: Starships, onClick: (Starships) -> Unit) {
            binding.starshipsName.text = starships.name
            binding.starshipsImage.setImageResource(starships.imageResId)
            binding.root.setOnClickListener {
                onClick(starships)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStarshipsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(starships[position], onClick)
    }

    override fun getItemCount() = starships.size
}
