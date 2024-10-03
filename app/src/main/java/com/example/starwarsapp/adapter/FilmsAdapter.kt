package com.example.starwarsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.ItemFilmsBinding


data class Films(
    val title: String,
    val imageResId: Int,
    val description: String
)

class FilmsAdapter(
    private val films: List<Films>,
    private val onClick: (Films) -> Unit
) : RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemFilmsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(films: Films, onClick: (Films) -> Unit) {
            binding.filmsTitle.text = films.title
            binding.filmsImage.setImageResource(films.imageResId)
            binding.root.setOnClickListener {
                onClick(films)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilmsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(films[position], onClick)
    }

    override fun getItemCount() = films.size
}
