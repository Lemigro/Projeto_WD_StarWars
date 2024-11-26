package com.example.starwarsapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.ItemFavoritesBinding
import com.example.starwarsapp.model.FavoritesModel

class FavoritesAdapter(
    private var favorites: MutableList<FavoritesModel>,
    private val onClick: (FavoritesModel) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemFavoritesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoritesModel, onClick: (FavoritesModel) -> Unit) {
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

    fun updateFavorites(newFavorites: List<FavoritesModel>) {
        favorites.clear()
        favorites.addAll(newFavorites)
        notifyDataSetChanged()
    }

    fun addFavorite(favorite: FavoritesModel) {
        favorites.add(favorite)
        notifyItemInserted(favorites.size - 1)
    }

    fun removeFavorite(favorite: FavoritesModel) {
        val index = favorites.indexOf(favorite)
        if (index >= 0) {
            favorites.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
