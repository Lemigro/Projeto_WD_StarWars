package com.example.starwarsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.ItemFavoritesBinding


data class Favorites(
    val title: String,
    val imageResId: Int,
    val description: String
)

class FavoritesAdapter(
    private val favorites: List<Favorites>,
    private val onClick: (Favorites) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemFavoritesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorites, onClick: (Favorites) -> Unit) {
            binding.favoritesTitle.text = favorite.title
            binding.favoritesImage.setImageResource(favorite.imageResId)
            binding.root.setOnClickListener { onClick(favorite) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favorites[position], onClick)
    }

    override fun getItemCount() = favorites.size
}
